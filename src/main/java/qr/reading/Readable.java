package qr.reading;

import com.google.zxing.NotFoundException;

import java.io.File;
import java.io.IOException;

public interface Readable {
    String readSecureQRCode(File qrCodeImage) throws IOException, NotFoundException;

    String readSecureQRCode(String filepath) throws IOException, NotFoundException;

    // readSecureQRCode(Byte[] qrCodeBytes) throws IOException;

}
