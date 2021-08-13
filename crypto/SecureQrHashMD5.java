import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecureQrHashMD5 implements SecureQrHash {
    @Override
    public String getHash(String message) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(message.getBytes(StandardCharsets.UTF_8));
        return SecureQrHash.byteToHexString(md5.digest());
    }
}
