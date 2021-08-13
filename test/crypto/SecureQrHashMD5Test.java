package crypto;

import junit.framework.TestCase;
import util.RandomString;

import java.security.NoSuchAlgorithmException;

public class SecureQrHashMD5Test extends TestCase {

    public void testGetHash() throws NoSuchAlgorithmException {
        String message = RandomString.getString(32);
        SecureQrHash a = new SecureQrHashMD5();
        SecureQrHashMD5 b = new SecureQrHashMD5();
        assertEquals(a.getHash(message), b.getHash(message));
    }
}