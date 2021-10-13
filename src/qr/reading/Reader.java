package qr.reading;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Reader implements Readable {
    /**
     * QR 코드를 읽어 값을 반환하는 함수
     * @param qrCodeImage QR 코드 이미지
     * @return QR 코드를 읽은 값
     */
    @Override
    public String readSecureQRCode(File qrCodeImage) throws IOException {
        try {
            BufferedImage bf = ImageIO.read(qrCodeImage);
            LuminanceSource source = new BufferedImageLuminanceSource(bf);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * QR 코드를 읽어 값을 반환하는 함수
     * @param filepath QR 코드 이미지 파일 경로
     * @return QR 코드를 읽은 값
     */
    @Override
    public String readSecureQRCode(String filepath) throws IOException {
        try {
            BufferedImage bf = ImageIO.read(new FileInputStream(filepath));
            LuminanceSource source = new BufferedImageLuminanceSource(bf);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);

            return result.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
