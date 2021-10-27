package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import crypto.SecureQrCryptoAES256;
import crypto.SecureQrCryptoArray;
import crypto.SecureQrHashMD5;
import junit.framework.TestCase;
import qr.generating.Generator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class GeneratorTest extends TestCase {
    /* QR 이미지 생성 요청시 생성 후 반환 테스트*/
    public void testingGenerator() throws Exception {
        /* ArrayList for Crypto Instance */
        SecureQrCryptoArray arr = new SecureQrCryptoArray();    // Controller에 @Autowired 어노테이션과 함께 인스턴스 멤버로 선언
        Generator gen = new Generator();

        SecureQrCryptoAES256 aes256 = new SecureQrCryptoAES256();
        aes256.setKey("00000000000000000000000000000000");

        arr.add(new SecureQrHashMD5(), aes256); // 신규 QR 생성 요청시


        /* QR 이미지 생성을 위해 요청한 데이터 */
        String authUrl = "https://secureQR.com//v1//secureQR//";
        String data = "https://github.com//SoTree17";
        int c_index = 0;
        int d_index = arr.addData(data);
        int width = 200;
        int height = 200;

        /* Generator 호출 */
        byte[] result = gen.createSecureQRCode(arr, authUrl, c_index, d_index, width, height);

        /* 이미지 지정된 path 로 성공적으로 저장했다면 true */
        assertTrue(gen.createSecureQRImage(result, 0, "./testImg.png"));
    }

    /* Testcase2  존재하지 않는 Path에 대해서 생성하려할때 */
    public void testingPathExistence() throws Exception {
        /* GSON 사용하면 CryptoArray없이 이미지 생성 가능 */
        SecureQrCryptoArray arr = new SecureQrCryptoArray();    // Controller에 @Autowired 어노테이션과 함께 인스턴스 멤버로 선언
        Generator gen = new Generator();

        SecureQrCryptoAES256 aes256 = new SecureQrCryptoAES256();
        aes256.setKey("00000000000000000000000000000000");

        arr.add(new SecureQrHashMD5(), aes256); // 신규 QR 생성 요청 가정

        /* QR 이미지 생성을 위해 요청한 데이터 */
        String authUrl = "https://secureQR.com//v1//secureQR//";
        String data = "https://github.com//SoTree17";
        int c_index = 0;
        int d_index = arr.addData(data);
        int width = 200;
        int height = 200;

        /* Generator 호출 */
        byte[] result = gen.createSecureQRCode(arr, authUrl, c_index, d_index, width, height);

        /* 이미지 지정된 path 로 성공적으로 저장했다면 true */
        LocalDate now = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dailyDirectory = dtf.format(now);
        String path = "ImageTest/Generator/"+dailyDirectory+"/";
        String fileName = "testImg.png";

        assertTrue(gen.createSecureQRImage(result, 0, path+fileName));
    }

    /* Testcase 3 - Fallible case */
    // param으로 받은 경로의 부모디렉토리가 없이 "파일이름만" 받았을 때 발생하는 경우를 테스트
    // 이 때 NullPointerException 발생 가능하고, 리턴으로는 false 반환함.

    public void testingFailureCase1() throws Exception {
        /* GSON 사용하면 CryptoArray없이 이미지 생성 가능 */
        SecureQrCryptoArray arr = new SecureQrCryptoArray();    // Controller에 @Autowired 어노테이션과 함께 인스턴스 멤버로 선언
        Generator gen = new Generator();

        SecureQrCryptoAES256 aes256 = new SecureQrCryptoAES256();
        aes256.setKey("00000000000000000000000000000000");

        arr.add(new SecureQrHashMD5(), aes256); // 신규 QR 생성 요청 가정

        /* QR 이미지 생성을 위해 요청한 데이터 */
        String authUrl = "https://secureQR.com//v1//secureQR//";
        String data = "https://github.com//SoTree17";
        int c_index = 0;
        int d_index = arr.addData(data);
        int width = 200;
        int height = 200;

        /* Generator 호출 */
        byte[] result = gen.createSecureQRCode(arr, authUrl, c_index, d_index, width, height);

        /* 파일 경로만 주면 NULL */

        String fileName = "testGenerateImg.png";

        assertFalse(gen.createSecureQRImage(result, 0, fileName));
    }
}
