package qr.creation;

import crypto.SecureQrCryptoArray;

import java.io.IOException;

public interface CreateSecureQR {
    public byte[] createSecureQRCode(SecureQrCryptoArray arr, String authUrl, String data, int index, int width, int height) throws IOException;

}
