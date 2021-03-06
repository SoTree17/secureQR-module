package crypto;

import junit.framework.TestCase;
import qr_util.RandomString;

import java.security.NoSuchAlgorithmException;

public class SecureQrHashMD5Test extends TestCase {

    /**
     * Test case for hasing method, MD5
     */
    public void testGetHash() throws NoSuchAlgorithmException {
        String message = RandomString.getString(32);
        SecureQrHash a = new SecureQrHashMD5();
        SecureQrHashMD5 b = new SecureQrHashMD5();
        assertEquals(a.hashing(message), b.hashing(message));
    }

    /**
     * Test case to check
     *
     */
    public void testGetType() throws NoSuchAlgorithmException {
        SecureQrHashMD5 md5 = new SecureQrHashMD5();
        assertEquals("HASH", md5.getInstanceType());
        assertEquals("MD5", md5.getMethodType());
    }
}