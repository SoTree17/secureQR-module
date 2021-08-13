package crypto;

import junit.framework.TestCase;
import util.RandomString;

import java.security.NoSuchAlgorithmException;

public class SecureQrHashSHA256Test extends TestCase {

    public void testGetHash() throws NoSuchAlgorithmException {
        String message = RandomString.getString(32);
        SecureQrHash a = new SecureQrHashSHA256();
        SecureQrHashSHA256 b = new SecureQrHashSHA256();
        assertEquals(a.getHash(message), b.getHash(message));
    }
}