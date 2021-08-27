package crypto;

import qr_util.RandomString;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class SecureQrCryptoAES256 implements SecureQrCrypto {
    public static final String METHOD_TYPE = "AES256";
    // public final String Method_TYPE = "AES256";
    @Override
    public String getMethodType() { return METHOD_TYPE; }

    public static String alg = "AES/CBC/PKCS5Padding"; //해당 문자열로부터, Chiper 객체 생성시, AES 암호화, ECB Operation mode, PKCS5 padding Scheme 으로 초기화하라고 요청
    private String key = "00000000000000000000000000000000";
    private String iv = key.substring(0, 16);

    public void setKey(String _key) {
        try {
            if (_key.length() == 32) {
                this.key = _key;
                this.iv = key.substring(0, 16);
            }
            else
                throw new Exception();
        } catch (Exception e) {
            System.out.println("Key 는 32자여야 합니다.");
        }
    }

    public String getKey() {
        return this.key;
    }

    public SecureQrCryptoAES256() {
        setKey(RandomString.getString(32));
    }

    @Override
    public String encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
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
        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
