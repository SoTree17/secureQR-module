package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import crypto.SecureQrCryptoAES256;
import crypto.SecureQrCryptoArray;
import crypto.SecureQrHashMD5;
import junit.framework.TestCase;
import qr.generating.Generator;


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
        int index = 0;
        int width = 200;
        int height = 200;

        /* Generator 호출 */
        byte[] result = gen.createSecureQRCode(arr, authUrl, data, index, width, height);

        /* 이미지 지정된 path 로 성공적으로 저장했다면 true */
        assertTrue(gen.createSecureQRImage(result, 0, "testImg.png"));
    }
}
