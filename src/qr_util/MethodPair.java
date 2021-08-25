package qr_util;

import crypto.SecureQrCrypto;
import crypto.SecureQrHash;

public class MethodPair {
    private SecureQrHash qrHash;
    private SecureQrCrypto qrCrypto;

    public MethodPair(SecureQrHash _h, SecureQrCrypto _c) {
        this.qrHash = _h;
        this.qrCrypto = _c;
    }

    public SecureQrCrypto getCrypto() {
        return this.qrCrypto;
    }

    public SecureQrHash getHash() {
        return this.qrHash;
    }
}
