package crypto;

import junit.framework.TestCase;
import qr_util.CryptoDict;
import qr_util.RandomString;


/**
 * 암호화된 URL, 암호화 여부, 암호화 방식과 관련된 index
 */
public class SecureQrCryptoArrayTest2 extends TestCase {

    /**
     * URL, 크립토 여부, 인덱스 DICT에서 찾기..
     */
    public void testAdd() throws Exception{
        String testURL = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=hello+world";
        SecureQrCryptoArray arr = new SecureQrCryptoArray();

        /* 해쉬 방식 md5 */
        SecureQrHash md5 = new SecureQrHashMD5();
        SecureQrHash md5_in = new SecureQrHashMD5();

        /* crpyto 방식 ase256 */
        SecureQrCryptoAES256 aes256 = new SecureQrCryptoAES256();
        SecureQrCryptoAES256 aes256_in = new SecureQrCryptoAES256();

        String randKey = RandomString.getString(32); // 32 비트 길이의 랜덤 key 값 가져옴
        aes256.setKey(randKey);
        aes256_in.setKey(randKey);

        /* 이렇게 해보면 괜찮으려나..?*/
        arr.add(md5_in, aes256_in, CryptoDict.AES256);

        String hashText1 = arr.getLastHash().hashing(testURL); // Method Pair 객체 타입의 ArrayList의 마지막 요소의 hash 값 가져와서 그 방식으로 해슁?
        String hastText2 = md5.hashing(testURL); // 그냥 md5 방식으로 해싱?

        String encryptedText1 = arr.getLastCrypto().encrypt(testURL); // 마지막 요소의 crypto 방식 가져와서 그 방식으로 해싱
        String encryptedText2 = aes256.encrypt(testURL); // 그냥 ase256 방식으로 encrypt

        /* 테스트 결과*/

        assertEquals(hashText1, hastText2);
        assertEquals(encryptedText1, encryptedText2);
        assertEquals(aes256.decrypt(encryptedText1), arr.getLastCrypto().decrypt(encryptedText2));


        String test1 = aes256.getMethodType();
        // test3 = aes256.METHOD_TYPE;
        String test2 = arr.getLastPair().getCryptoDict().name();
        /* decrypt 방식 테스트 */
        //assertEquals(aes256.getInstanceType(), arr.getLastPair().getCryptoDict().name());
    }
}
