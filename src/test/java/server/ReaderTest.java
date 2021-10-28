package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import crypto.SecureQrCryptoAES256;
import crypto.SecureQrCryptoArray;
import crypto.SecureQrCryptoRSA;
import crypto.SecureQrHashMD5;
import junit.framework.TestCase;
import qr.generating.Generator;
import qr.reading.Readable;
import qr.reading.Reader;

import java.io.File;
import java.io.FileNotFoundException;

public class ReaderTest extends TestCase {
    /* TestCase 1 - QR 생성하고 읽고 인증까지 하는 케이스 */
    // 이미지 경로 path는 Directoty + filename으로 형태로 전달되는 경우
    public void testFileImageReader() throws Exception {
        SecureQrCryptoArray arr = new SecureQrCryptoArray();
        Generator gen = new Generator();
        Readable read = new Reader();
        String THISPATH = "ImageTest/Reader/generatingForReading.png";

        SecureQrCryptoAES256 aes256 = new SecureQrCryptoAES256();
        aes256.setKey("00000000000000000000000000000000");

        arr.add(new SecureQrHashMD5(), aes256);

        /* QR 이미지 생성을 위해 요청한 데이터 */
        String authUrl = "https://secureQR.com//v1//secureQR//";
        String data = "https://github.com//SoTree17";
        int c_index_user = 0;
        int d_index_user = arr.addData(data);
        int width = 200;
        int height = 200;

        /* Generator 호출해서 파일 저장 */
        byte[] binaryImg = gen.createSecureQRCode(arr, authUrl, c_index_user, d_index_user, width, height);
        gen.createSecureQRImage(binaryImg, 0, THISPATH);

        /* suppose File is given as a parameter */
        File file = new File(THISPATH);

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

        assertNotNull(originUrl);
        assertNotNull(hash_value);

    }

    /* Testcase 2 - 파일 경로로부터 읽기 */
    public void testReadFromPath() throws Exception {
        /* 보안 QR 이미지 생성을 위해 요청한 데이터 ex) */
        SecureQrCryptoArray arr = new SecureQrCryptoArray();
        SecureQrCryptoRSA rsa = new SecureQrCryptoRSA();
        arr.add(new SecureQrHashMD5(), rsa);

        String authUrl = "https://secureQR.com//v1//secureQR//";
        String data = "https://github.com//SoTree17";
        int c_index_user = 0;
        int d_index_user = arr.addData(data);
        int width = 200;
        int height = 200;

        /* step2) 보안 QR 이미지 파일 생성*/
        Generator gen = new Generator();
        String THISPATH = "ImageTest/Reader/generatingForReading2.png";
        byte[] binaryImg = gen.createSecureQRCode(arr, authUrl, c_index_user, d_index_user, width, height);
        gen.createSecureQRImage(binaryImg, 0, THISPATH);

        /* step3) 보안 QR 이미지 파일 경로로 읽기 */
        Reader reader = new Reader();
        String result = reader.readSecureQRCode(THISPATH);

        assertNotNull(result);
    }


    /* Testcase 3 - Fallible case */
    // 파일명으로만 인자로서 전달하는 경우 e.printStackTrace();과 읽을 파일이 없어서 IIOException이 발생하게 됨.
    // 따라서 이때, result는 null 값이므로 assertNull(result)가 예상과 맞는지 테스트 진행함.
    public void testFallableReadTest() throws Exception {
        SecureQrCryptoArray arr = new SecureQrCryptoArray();

        SecureQrCryptoAES256 aes256 = new SecureQrCryptoAES256();
        aes256.setKey("00000000000000000000000000000000");

        arr.add(new SecureQrHashMD5(), aes256);

        Readable read = new Reader();
        /* suppose File is given as a parameter */
        String path = "testReadingImg.png"; // 서버에서 Params
        File file = new File(path);

        String result = read.readSecureQRCode(file);
        // result = read.readSecureQRCode("testImg.png");
        assertNull(result);
    }

    /* Testcase 4 - Fallible case */
    // 파일 경로가 유효하지 않을 때 FileNotFound Exception 이 발생 가능함.
    // 따라서 이때, result는 null 값이므로, assertNull(result)가 예상과 맞는지 테스트 진행함.
    public void testFallibleNotAvailPath() throws Exception {
        /* step1) 보안 QR 이미지 생성을 위해 요청한 데이터 생성 */
        SecureQrCryptoArray arr = new SecureQrCryptoArray();
        SecureQrCryptoRSA rsa = new SecureQrCryptoRSA();
        arr.add(new SecureQrHashMD5(), rsa);

        String authUrl = "https://secureQR.com//v1//secureQR//";
        String data = "https://github.com//SoTree17";
        int c_index_user = 0;
        int d_index_user = arr.addData(data);
        int width = 200;
        int height = 200;

        /* step2) 보안 QR 이미지 파일 생성*/
        Generator gen = new Generator();
        String THISPATH = "ImageTest/Reader/generatingForReading2.png";
        byte[] binaryImg = gen.createSecureQRCode(arr, authUrl, c_index_user, d_index_user, width, height);
        gen.createSecureQRImage(binaryImg, 0, THISPATH);

        /* step3) 잘못된 경로로 읽기  */
        String result = "";
        try {
            Reader reader = new Reader();
            String INVALID_PATH = "OSS/Secure-Module/generatingForReading2.png";
            result = reader.readSecureQRCode(INVALID_PATH);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertNull(result);
    }
}
