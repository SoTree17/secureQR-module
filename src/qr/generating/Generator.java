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

    @Override
    public byte[] createSecureQRCode(SecureQrCryptoArray arr, String authUrl, String data, int index, int width, int height) throws IOException {
        try {
            JsonObject obj = new JsonObject();
            String hash_value = arr.getHash(index).hashing(data);
            String data_hash = data + ";;" + hash_value;
            String encrypted_data = arr.getCrypto(index).encrypt(data_hash);

            obj.addProperty("requestURL", authUrl);
            obj.addProperty("index", index);
            obj.addProperty("data", encrypted_data);

            String serialized_data = new Gson().toJson(obj);

            /* Generating QR Code */
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

    @Override
    public boolean createSecureQRImage(byte[] qr_byte_arr, int off, String path) {
        boolean status;
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
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
