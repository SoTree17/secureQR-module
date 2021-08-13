package crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecureQrHashMD5 implements SecureQrHash {
    public static final String METHOD_TYPE = "MD5";
    @Override
    public String getMethodType() {
        return METHOD_TYPE;
    }

    @Override
    public String getHash(String message) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(message.getBytes(StandardCharsets.UTF_8));
        return SecureQrHash.byteToHexString(md5.digest());
    }
}
