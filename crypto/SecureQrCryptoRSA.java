package crypto;

import java.security.*;
import java.util.Base64;
import javax.crypto.Cipher;

public class SecureQrCryptoRSA implements SecureQrCrypto {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    /**
     * 생성자에서 RSA 키 쌍 생성
     */
    SecureQrCryptoRSA() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator gen;
        gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(1024, secureRandom);

        KeyPair keyPair = gen.genKeyPair();

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
        byte[] bytePlain = cipher.doFinal(message.getBytes());

        return Base64.getEncoder().encodeToString(bytePlain);
    }


    /**
     * Private Key 로 message 복호화
     */
    @Override
    public String decrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] byteEncrypted = Base64.getDecoder().decode(message.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        byte[] bytePlain = cipher.doFinal(byteEncrypted);

        return new String(bytePlain, "utf-8");
    }
}
