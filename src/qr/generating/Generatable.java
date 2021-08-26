package qr.generating;

import crypto.SecureQrCryptoArray;
import java.io.IOException;

public interface Generatable {

    byte[] createSecureQRCode(SecureQrCryptoArray arr, String authUrl, String data, int index, int width, int height) throws IOException;

    boolean createSecureQRImage(byte[] qr_byte_arr, int off, String path);

}
