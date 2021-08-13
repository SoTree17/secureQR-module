package crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecureQrHashSHA256 implements SecureQrHash {
    @Override
    public String getHash(String message) throws NoSuchAlgorithmException {
        MessageDigest sha265 = MessageDigest.getInstance("SHA-256");
        sha265.update(message.getBytes());
        return SecureQrHash.byteToHexString(sha265.digest());
    }
}
