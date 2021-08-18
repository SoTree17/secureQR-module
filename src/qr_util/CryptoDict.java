package qr_util;

/**
 * 암호화 여부 판단을 위한
 * 인덱스용?
 * 준호
 */
public enum CryptoDict {
    RSA, AES256
    /*RSA("/RSA"), AES256("/AES256");
    private final String value;

    CryptoDict(String value) { this.value = value; }
    public String getValue() { return value;}
    public static CryptoDict findByEncryption(String method){
        for(CryptoDict dict : CryptoDict.values()){
            if(dict.equals(method))
                return dict;
        }
    }*/
}
