package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import crypto.SecureQrCryptoAES256;
import crypto.SecureQrCryptoArray;
import crypto.SecureQrHashMD5;
import junit.framework.TestCase;
import qr.reading.Readable;
import qr.reading.Reader;

import java.io.File;

public class ReaderTest extends TestCase {

    public void testFileImageReader() throws Exception{
        SecureQrCryptoArray arr = new SecureQrCryptoArray();

        SecureQrCryptoAES256 aes256 = new SecureQrCryptoAES256();
        aes256.setKey("00000000000000000000000000000000");

        arr.add(new SecureQrHashMD5(), aes256);

        Readable read = new Reader();
        /* suppose File is given as a parameter */
        String path = "testImg.png"; // 서버에서 Params
        File file = new File(path);

        String result = read.readSecureQRCode(file);
        // result = read.readSecureQRCode("testImg.png");

        /* JSON 값 얻기*/
        JsonObject obj = new Gson().fromJson(result, JsonObject.class);
        int c_index = obj.get("c_index").getAsInt();
        String encrypted = obj.get("data").getAsString();

        /*암호화된 데이터 복호화*/
        String decrypted = arr.getCrypto(c_index).decrypt(encrypted); // index 번호에 있는 element 인스턴스 접근 후 그 복호화방법
        String[] splitDecrypted = decrypted.split(";;"); // data;;hash_value 규약

        String originUrl = splitDecrypted[0];
        String hash_value = splitDecrypted[1];

        // System.out.println(originUrl);
        // System.out.println(hash_value);

        assertNotNull(originUrl);
        assertNotNull(hash_value);

    }
}
