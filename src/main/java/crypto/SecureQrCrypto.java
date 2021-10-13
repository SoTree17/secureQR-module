package crypto;

/**
 * QR 코드 암호화 인터페이스
 */
public interface SecureQrCrypto {
    String INSTANCE_TYPE = "CRYPTO";

    default String getInstanceType() {
        return INSTANCE_TYPE;
    }

    String getMethodType();
    String encrypt(String message) throws Exception;
    String decrypt(String message) throws Exception;
}
