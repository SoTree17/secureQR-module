package crypto;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecureQrCryptoAES256 implements SecureQrCrypto {
    public static String alg = "AES/CBC/PKCS5Padding";
    private String key = "00000000000000000000000000000000000";
    private final String iv = key.substring(0, 16);

    public void setSecretKey(String _key) throws Exception {
        try {
            if (_key.length() == 32)
                this.key = _key;
            else
                throw new Exception();
        } catch (Exception e) {
            System.out.println("Key 는 32자여야 합니다.");
        }

    }

    @Override
    public String encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(message.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    @Override
    public String decrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(message);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }
}
