package crypto;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import util.RandomString;
import junit.framework.TestCase;

public class SecureQrCryptoAES256Test extends TestCase {

    public void testSetSecretKey() {
        SecureQrCryptoAES256 aes = new SecureQrCryptoAES256();
        String newKey = RandomString.getString(32);
        aes.setKey(newKey);

        assertEquals(aes.getKey(), newKey);
    }

    public void testEncryptDecrypt() throws Exception {
        String origin = RandomString.getString(16);

        SecureQrCryptoAES256 aes256 = new SecureQrCryptoAES256();
        SecureQrCrypto myCrpyto = aes256;
        String cipherText = myCrpyto.encrypt(origin);

        assertEquals(origin, myCrpyto.decrypt(cipherText));
    }

    public void testGetType() {
        SecureQrCryptoAES256 aes = new SecureQrCryptoAES256();
        assertEquals("CRYPTO", aes.getInstanceType());
        assertEquals("AES256", aes.getMethodType());
    }
}