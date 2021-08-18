package qr_util;

import crypto.SecureQrCrypto;
import crypto.SecureQrHash;

public class MethodPair {
    private SecureQrHash qrHash;
    private SecureQrCrypto qrCrypto;
    /**
     * 어떤 방식으로 Crypto되었는지 숫자로 저장하고 싶은데..
     */
    private CryptoDict crypto_method;


    public MethodPair(SecureQrHash _h, SecureQrCrypto _c) {
        this.qrHash = _h;
        this.qrCrypto = _c;
    }

    public MethodPair(SecureQrHash _h, SecureQrCrypto _c, CryptoDict _m){
        this.qrHash = _h;
        this.qrCrypto = _c;
        this.crypto_method = _m;
    }

    public SecureQrCrypto getCrypto() {
        return this.qrCrypto;
    }

    public SecureQrHash getHash() {
        return this.qrHash;
    }

    public CryptoDict getCryptoDict() { return this.crypto_method; }
}
