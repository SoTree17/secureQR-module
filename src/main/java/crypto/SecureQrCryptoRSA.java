package crypto;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import javax.crypto.Cipher;

public class SecureQrCryptoRSA implements SecureQrCrypto {
    public static final String METHOD_TYPE = "RSA";
    @Override
    public String getMethodType() {
        return METHOD_TYPE;
    }

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    /**
     * 생성자에서 RSA 키 쌍 생성
     */
    public SecureQrCryptoRSA() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyGen;
        keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024, secureRandom);

        KeyPair keyPair = keyGen.genKeyPair();

        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    /**
     * Public Key 로 message 암호화
     */
    @Override
    public String encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());

        return Base64.getEncoder().encodeToString(encryptedMessage);
    }


    /**
     * Private Key 로 message 복호화
     */
    @Override
    public String decrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] byteEncrypted = Base64.getDecoder().decode(message.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        byte[] decryptedMessage = cipher.doFinal(byteEncrypted);

        return new String(decryptedMessage, StandardCharsets.UTF_8);
    }
}
