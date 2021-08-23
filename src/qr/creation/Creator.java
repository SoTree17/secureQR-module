package qr.creation;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import crypto.SecureQrCryptoArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Creator implements CreateSecureQR {

    @Override
    public byte[] createSecureQRCode(SecureQrCryptoArray arr, String authUrl, String data, int index, int width, int height) throws IOException {
        try {
            JsonObject obj = new JsonObject();
            String hash_value = arr.getHash(index).hashing(data);
            String data_hash = data + ";;" + hash_value;
            String encrypted_data = arr.getCrypto(index).encrypt(data_hash);

            String requestUrl = authUrl + encrypted_data;


            obj.addProperty("requestUrl", requestUrl);
            obj.addProperty("index", index);
            obj.addProperty("test", encrypted_data); // 임시 테스트 용

            String serialized_data = new Gson().toJson(obj);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(serialized_data, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayStream);
            // to byte array
            return byteArrayStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }
}