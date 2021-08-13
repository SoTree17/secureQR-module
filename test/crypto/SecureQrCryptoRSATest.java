package crypto;

import junit.framework.TestCase;
import util.RandomString;

public class SecureQrCryptoRSATest extends TestCase {

    public void testEncryptDecrypt() throws Exception {
        String origin = RandomString.getString(16);

        SecureQrCryptoRSA rsa = new SecureQrCryptoRSA();
        SecureQrCrypto myCrpyto = rsa;
        String cipherText = myCrpyto.encrypt(origin);

        assertEquals(origin, myCrpyto.decrypt(cipherText));
    }
}