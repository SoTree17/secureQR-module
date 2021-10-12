package crypto;

import junit.framework.TestCase;
import qr_util.RandomString;
import java.security.NoSuchAlgorithmException;

public class SecureQrHashSHA512Test extends TestCase {

    public void testGetHash() throws NoSuchAlgorithmException {
        String message = RandomString.getString(32);
        SecureQrHash a = new SecureQrHashSHA512();
        SecureQrHashSHA512 b = new SecureQrHashSHA512();
        assertEquals(a.hashing(message), b.hashing(message));
    }

    public void testGetType() {
        SecureQrHashSHA512 sha512 = new SecureQrHashSHA512();
        assertEquals("HASH", sha512.getInstanceType());
        assertEquals("SHA512", sha512.getMethodType());
    }
}