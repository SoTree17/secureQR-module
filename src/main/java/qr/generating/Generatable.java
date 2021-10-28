package qr.generating;

import crypto.SecureQrCryptoArray;

import java.io.IOException;

public interface Generatable {

    byte[] createSecureQRCode(SecureQrCryptoArray arr, String authUrl, int c_index, int d_index, int width, int height) throws IOException;

    boolean createSecureQRImage(byte[] qr_byte_arr, int off, String path) throws NullPointerException;

}
