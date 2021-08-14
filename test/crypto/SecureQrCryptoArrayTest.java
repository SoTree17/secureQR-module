package crypto;

import junit.framework.TestCase;
import util.RandomString;

import java.security.NoSuchAlgorithmException;

public class SecureQrCryptoArrayTest extends TestCase {

    public void testAdd() throws Exception {
        String origin = RandomString.getString(16);
        SecureQrCryptoArray arr = new SecureQrCryptoArray();

        SecureQrHash md5 = new SecureQrHashMD5();
        SecureQrHash md5_in = new SecureQrHashMD5();

        SecureQrCryptoAES256 aes256 = new SecureQrCryptoAES256();
        SecureQrCryptoAES256 aes256_in = new SecureQrCryptoAES256();

        String randKey = RandomString.getString(32);
        aes256.setKey(randKey);
        aes256_in.setKey(randKey);

        arr.add(md5_in, aes256_in);

        String hashText1 = arr.getLastHash().hashing(origin);
        String hashText2 = md5.hashing(origin);

        String encryptedText1 = aes256.encrypt(origin);
        String encryptedText2 = arr.getLastCrypto().encrypt(origin);

        assertEquals(hashText1, hashText2);
        assertEquals(encryptedText1, encryptedText2);
        assertEquals(aes256.decrypt(encryptedText1), arr.getLastCrypto().decrypt(encryptedText2));
    }

    public void testGetObject() throws Exception {
        String origin = RandomString.getString(16);
        SecureQrCryptoArray arr = new SecureQrCryptoArray();

        SecureQrCryptoRSA rsa = new SecureQrCryptoRSA();
        SecureQrHashSHA256 sha256 = new SecureQrHashSHA256();

        for (int i = 0; i < 10; i++) {
            if(i == 7) {
                arr.add(sha256, rsa);
                continue;
            }
            arr.add(new SecureQrHashMD5(), new SecureQrCryptoAES256());
        }
        String hashedText = sha256.hashing(origin);
        assertEquals(hashedText, arr.getHash(7).hashing(origin));

        String encryptedText = rsa.encrypt(origin);
        assertEquals(rsa.decrypt(encryptedText), arr.getCrypto(7).decrypt(encryptedText));

        assertEquals(sha256, arr.getHash(7));
        assertEquals(rsa, arr.getCrypto(7));
    }
}