package crypto;

import junit.framework.TestCase;
import qr_util.RandomString;

import java.security.NoSuchAlgorithmException;

public class SecureQrCryptoRSATest extends TestCase {

    /**
     * Test case for RSA Encryption and decryption
     */
    public void testEncryptDecrypt() throws Exception {
        String origin = RandomString.getString(16);

        SecureQrCryptoRSA rsa = new SecureQrCryptoRSA();
        SecureQrCrypto myCrpyto = rsa;
        String cipherText = myCrpyto.encrypt(origin);

        assertEquals(origin, myCrpyto.decrypt(cipherText));
    }


    /**
     * Test case to check
     * 1) does it encrypted?
     * 2) what is it's encryption method?
     * When SecureQrCrypto Instance given
     */
    public void testGetType() throws NoSuchAlgorithmException {
        SecureQrCryptoRSA rsa = new SecureQrCryptoRSA();
        assertEquals("CRYPTO", rsa.getInstanceType());
        assertEquals("RSA", rsa.getMethodType());
    }
}