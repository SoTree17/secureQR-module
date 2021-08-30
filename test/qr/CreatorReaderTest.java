package qr;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import crypto.SecureQrCryptoAES256;
import crypto.SecureQrCryptoArray;
import crypto.SecureQrHashMD5;
import junit.framework.TestCase;
import qr.generating.Generatable;
import qr.generating.Generator;
import qr.reading.Reader;
import qr.reading.Readable;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;

public class CreatorReaderTest extends TestCase {

    public void testCreateSecureQRCode() throws Exception {
        Generatable app = new Generator();
        Readable read = new Reader();
        SecureQrCryptoArray arr = new SecureQrCryptoArray();
        arr.add(new SecureQrHashMD5(), new SecureQrCryptoAES256());

        String authUrl = "~~myserver.com/";
        String data = "https://github.com/SoTree17";

        int c_index = 0;
        int d_index = arr.addData(data);

        // qr코드 생성
        byte[] qr_byte_arr = app.createSecureQRCode(arr, authUrl, c_index, d_index, 256, 256);

        // qr코드를 읽어서 값이 맞는지 확인
        FileImageOutputStream imageOutput = new FileImageOutputStream(new File("image.png"));
        imageOutput.write(qr_byte_arr, 0, qr_byte_arr.length);
        imageOutput.close();

        String result = read.readSecureQRCode("image.png");
        System.out.println("QR 코드 값 : ");
        System.out.println(result);

        // json 값 읽기
        JsonObject obj = new Gson().fromJson(result, JsonObject.class);
        int read_c_index = obj.get("c_index").getAsInt();
        int read_d_index = obj.get("d_index").getAsInt();
        String encrypted = obj.get("data").getAsString();

        // 암호화된 데이터 복호화
        String decrypted = arr.getCrypto(read_c_index).decrypt(encrypted);
        System.out.println("암호화 복호화 된 값 : ");
        System.out.println(encrypted);
        System.out.println(decrypted);
        String[] splitDecrypted = decrypted.split(";;");

        String originUrl = splitDecrypted[0];
        String hashUrl = splitDecrypted[1];
        System.out.println("원본 URL, URL 해시 값 : ");
        System.out.println(originUrl);
        System.out.println(hashUrl);

        // 원본 데이터 - QR 복호화 데이터 비교
        assertEquals(arr.getData(read_d_index), originUrl);
        // 원본 데이터 해쉬 값 - QR 해쉬 값 비교
        assertEquals(hashUrl, arr.getHash(read_c_index).hashing(data));
    }
}
