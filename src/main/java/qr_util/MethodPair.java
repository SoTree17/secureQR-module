package qr_util;

import crypto.SecureQrCrypto;
import crypto.SecureQrHash;

public class MethodPair {
    private SecureQrHash qrHash;
    private SecureQrCrypto qrCrypto;

    /**
     * 해시 함수와 암호화 함수를 쌍으로 저장한다.
     * @param _h 해시 방식
     * @param _c 암호화 방식
     */
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
