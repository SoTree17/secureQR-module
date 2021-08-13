package crypto;

import java.security.NoSuchAlgorithmException;

public class hashTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String origin = "Hello World!";

        // 해시 테스트
        SecureQrHash md5 = new SecureQrHashMD5();
        SecureQrHash sha256 = new SecureQrHashSHA256();

        System.out.println(md5.getHash(origin));
        System.out.println(sha256.getHash(origin));
    }
}
