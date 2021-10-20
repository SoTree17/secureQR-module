package qr.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import crypto.SecureQrCryptoAES256;
import crypto.SecureQrCryptoArray;
import crypto.SecureQrHashMD5;
import junit.framework.TestCase;

public class AuthQRTest extends TestCase {

    public void testIsSecureQR() throws Exception {
        /* ArrayList for Crypto Instance */
        SecureQrCryptoArray arr = new SecureQrCryptoArray();
        arr.add(new SecureQrHashMD5(), new SecureQrCryptoAES256());

        String authUrl = "~~myserver.com/";
        String data = "https://github.com/SoTree17";
        int c_index = 0;
        int d_index = arr.addData(data);

        /* convert into JsonObject */
        JsonObject obj = new JsonObject();
        String hash_value = arr.getHash(c_index).hashing(data);
        String data_hash = data + ";;" + hash_value;
        String encrypted_data = arr.getCrypto(c_index).encrypt(data_hash);

        obj.addProperty("requestURL", authUrl);
        obj.addProperty("c_index", c_index);
        obj.addProperty("d_index", d_index);
        obj.addProperty("data", encrypted_data);

        String serialized_data = new Gson().toJson(obj);

        AuthQR authQR = new AuthQR(arr);
        boolean a = AuthQR.isSecureQR(serialized_data);
        assertTrue(a);
    }

    public void testGetOriginData() throws Exception {
        SecureQrCryptoArray arr = new SecureQrCryptoArray();
        arr.add(new SecureQrHashMD5(), new SecureQrCryptoAES256());

        String data = "https://github.com/SoTree17";
        int c_index = 0;
        int d_index = arr.addData(data);

        String hash_value = arr.getHash(c_index).hashing(data);
        String data_hash = data + ";;" + hash_value;

        String encrypted = arr.getCrypto(c_index).encrypt(data_hash);

        AuthQR authQR = new AuthQR(arr);


        String normalCase = authQR.getOriginData(encrypted, c_index, d_index);
        assertEquals(normalCase, data);


        encrypted = arr.getCrypto(c_index).encrypt("asdf");
        String dataError = authQR.getOriginData(encrypted, c_index, d_index);
        assertEquals(dataError, AuthQR.DATA_ERROR);


        hash_value = "asdf";
        data_hash = data + ";;" + hash_value;
        encrypted = arr.getCrypto(c_index).encrypt(data_hash);
        String hashError = authQR.getOriginData(encrypted, c_index, d_index);
        assertEquals(hashError, AuthQR.HASH_ERROR);
    }
    public void testWrongParameter() throws Exception{
        SecureQrCryptoArray arr = new SecureQrCryptoArray();
        AuthQR authQR = new AuthQR(arr);
        authQR.getOriginData("asdf", 99, 99);
    }
}