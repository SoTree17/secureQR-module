package crypto;

import qr_util.RandomString;
import junit.framework.TestCase;

public class SecureQrCryptoAES256Test extends TestCase {

    public void testSetSecretKey() {
        SecureQrCryptoAES256 aes = new SecureQrCryptoAES256();
        String newKey = RandomString.getString(32);
        aes.setKey(newKey);

        assertEquals(aes.getKey(), newKey);
    }

    public void testEncryptDecrypt() throws Exception {
        String origin = RandomString.getString(500);

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

    public void testWithHash() throws Exception {
        String origin = RandomString.getString(500);

        boolean passed = true;

        SecureQrCrypto aes256 = new SecureQrCryptoAES256();

        SecureQrHash md5 = new SecureQrHashMD5();
        SecureQrHash sha256 = new SecureQrHashSHA256();
        SecureQrHash sha512 = new SecureQrHashSHA512();

        SecureQrHash[] hashes = { md5, sha256, sha512 };

        for(SecureQrHash h: hashes) {
            String hashed = h.hashing(origin);
            String cipherText = aes256.encrypt(hashed);
            String decrypted = aes256.decrypt(cipherText);

            if(!hashed.equals(decrypted)) {
                passed = false;
            }
        }
        assertTrue(passed);
    }
}