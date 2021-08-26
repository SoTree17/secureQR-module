package qr.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import crypto.SecureQrCryptoAES256;
import crypto.SecureQrCryptoArray;
import crypto.SecureQrHashMD5;
import crypto.SecureQrHashSHA256;
import junit.framework.TestCase;

import java.security.NoSuchAlgorithmException;

public class AuthQRTest extends TestCase {

    public void testIsSecureQR() throws Exception {
        /* ArrayList for Crypto Instance */
        SecureQrCryptoArray arr = new SecureQrCryptoArray();
        arr.add(new SecureQrHashMD5(), new SecureQrCryptoAES256());

        String authUrl = "~~myserver.com/";
        String data = "https://github.com/SoTree17";
        int index = 0;

        /* convert into JsonObject */
        JsonObject obj = new JsonObject();
        String hash_value = arr.getHash(index).hashing(data);
        String data_hash = data + ";;" + hash_value;
        String encrypted_data = arr.getCrypto(index).encrypt(data_hash);

        String requestUrl = authUrl + encrypted_data;

        obj.addProperty("requestUrl", requestUrl);
        obj.addProperty("index", index);
        obj.addProperty("test", encrypted_data); // 임시 테스트 용

        String serialized_data = new Gson().toJson(obj);

        AuthQR authQR = new AuthQR(arr, authUrl, data);
        boolean a = authQR.isSecureQR(serialized_data);
        assertTrue(a);
    }

    public void testGetOriginData() throws Exception {
        SecureQrCryptoArray arr = new SecureQrCryptoArray();
        arr.add(new SecureQrHashMD5(), new SecureQrCryptoAES256());

        String authUrl = "~~myserver.com/";
        String data = "https://github.com/SoTree17";
        int index = 0;

        String hash_value = arr.getHash(index).hashing(data);
        String data_hash = data + ";;" + hash_value;

        String encrypted = arr.getCrypto(index).encrypt(data_hash);

        AuthQR authQR = new AuthQR(arr, authUrl, data);


        String normalCase = authQR.getOriginData(encrypted, index);
        assertEquals(normalCase, data);


        encrypted = arr.getCrypto(index).encrypt("asdf");
        String dataError = authQR.getOriginData(encrypted, index);
        assertEquals(dataError, AuthQR.DATA_ERROR);


        hash_value = "asdf";
        data_hash = data + ";;" + hash_value;
        encrypted = arr.getCrypto(index).encrypt(data_hash);
        String hashError = authQR.getOriginData(encrypted, index);
        assertEquals(hashError, AuthQR.HASH_ERROR);
    }
}