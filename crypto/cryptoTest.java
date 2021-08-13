package crypto;

public class cryptoTest {
    public static void main(String[] args) throws Exception {
        String origin = "Hello World!";
        SecureQrCrypto myCrpyto;

        // AES 암호화 테스트
        SecureQrCryptoAES256 aes256 = new SecureQrCryptoAES256();
        aes256.setSecretKey("01234567890123456789012345678901");
        myCrpyto = aes256;
        String cipherText = myCrpyto.encrypt(origin);
        System.out.println(cipherText);
        System.out.println(myCrpyto.decrypt(cipherText));
        
        
        // RSA 암호화 테스트
        SecureQrCryptoRSA rsa = new SecureQrCryptoRSA();
        myCrpyto = rsa;
        cipherText = myCrpyto.encrypt(origin);
        System.out.println(cipherText);
        System.out.println(myCrpyto.decrypt(cipherText));
    }
}
