package qr.reading;

import java.io.File;
import java.io.IOException;

public interface Readable {
    String readSecureQRCode(File qrCodeImage) throws IOException;

    String readSecureQRCode(String filepath) throws IOException;

}
