package qr.generating;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import crypto.SecureQrCryptoArray;

import javax.imageio.stream.FileImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Generator implements Generatable {

    /**
     * 보안 QR코드 생성 함수
     *
     * @param arr     사용할 해시, 암호화, 데이터가 저장되어있는 SecureQrCryptoArray
     * @param authUrl 보안 QR 코드 인증에 사용할 URL
     * @param c_index 사용할 해시, 암호화의 index
     * @param d_index 사용할 데이터의 index
     * @param width   QR 코드 크기 조절
     * @param height  QR 코드 크기 조절
     * @return 보안 QR의 byte array
     */
    @Override
    public byte[] createSecureQRCode(SecureQrCryptoArray arr, String authUrl, int c_index, int d_index, int width, int height) throws IOException {
        JsonObject obj = new JsonObject();
        String data = arr.getData(d_index);
        try {
            String hash_value = arr.getHash(c_index).hashing(data);
            String data_hash = data + ";;" + hash_value;
            String encrypted_data = arr.getCrypto(c_index).encrypt(data_hash);

            obj.addProperty("requestURL", authUrl);
            obj.addProperty("c_index", c_index);
            obj.addProperty("d_index", d_index);
            obj.addProperty("data", encrypted_data);

            String serialized_data = new Gson().toJson(obj);

            /* Generating QR Code */
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(serialized_data, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayStream);
            /* to byte array */
            return byteArrayStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    /**
     * byte arrary 를 이미지 파일로 저장하는 함수
     * 파일경로를 받아서, 만약 부모 경로가 존재하지 않으면 새로 만들고 파일을 저장함.
     *
     * @param qr_byte_arr QR 코드의 byte array
     * @param off         시작 오프셋
     * @param path        저장 경로 (디렉토리+파일명)
     * @return 이미지 생성 성공 여부
     * @exception NullPointerException param으로 받은 경로의 부모디렉토리가 없이 "파일이름만" 받았을 때 발생 가능
     */
    @Override
    public boolean createSecureQRImage(byte[] qr_byte_arr, int off, String path) {
        boolean status;
        try {
            File qrImg = new File(path);
            if (!qrImg.getParentFile().exists()) {
                qrImg.getParentFile().mkdirs();
            }
            FileImageOutputStream imageOutput = new FileImageOutputStream(qrImg);
            imageOutput.write(qr_byte_arr, off, qr_byte_arr.length);
            imageOutput.close();
            status = true;
        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}
