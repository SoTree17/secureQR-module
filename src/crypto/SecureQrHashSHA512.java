package crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecureQrHashSHA512 implements SecureQrHash {
    public static final String METHOD_TYPE = "SHA512";
    @Override
    public String getMethodType() {
        return METHOD_TYPE;
    }

    @Override
    public String hashing(String message) throws NoSuchAlgorithmException {
        MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
        sha512.update(message.getBytes());
        return SecureQrHash.byteToHexString(sha512.digest());
    }
}
