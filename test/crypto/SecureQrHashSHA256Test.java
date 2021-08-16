package crypto;

import junit.framework.TestCase;
import qr_util.RandomString;

import java.security.NoSuchAlgorithmException;

public class SecureQrHashSHA256Test extends TestCase {

    public void testGetHash() throws NoSuchAlgorithmException {
        String message = RandomString.getString(32);
        SecureQrHash a = new SecureQrHashSHA256();
        SecureQrHashSHA256 b = new SecureQrHashSHA256();
        assertEquals(a.hashing(message), b.hashing(message));
    }

    public void testGetType() throws NoSuchAlgorithmException {
        SecureQrHashSHA256 sha256 = new SecureQrHashSHA256();
        assertEquals("HASH", sha256.getInstanceType());
        assertEquals("SHA256", sha256.getMethodType());
    }
}