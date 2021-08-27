package qr.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import crypto.SecureQrCryptoArray;

public class AuthQR {
    public String authUrl;
    public String data;
    public SecureQrCryptoArray arr;

    public final static String DATA_ERROR = "원본 데이터와 다름";
    public final static String HASH_ERROR = "해시 값이 다름";

    /**
     * SecureQR 코드 인증에 필요한 값 초기화
     *
     * @param arr 암호화 배열
     * @param authUrl 원본 authUrl
     * @param data 원본 data
     */
    public AuthQR(SecureQrCryptoArray arr, String authUrl, String data) {
        this.arr = arr;
        this.authUrl = authUrl;
        this.data = data;
    }

    /**
     * 올바른 SecureQR 인지 체크하는 함수
     * @param read_data SecureQR 을 읽은 데이터
     * @return SecureQR 이면 True 반환
     */
    public static boolean isSecureQR(String read_data) {
        try {
            JsonObject obj = new Gson().fromJson(read_data, JsonObject.class);
            int index = obj.get("index").getAsInt();
            String requestUrl = obj.get("requestUrl").getAsString();

            return true;
        }
        catch (JsonSyntaxException e) {
            System.out.println("올바르지 않은 Json 문법");
            return false;
        }
        catch (NullPointerException e) {
            System.out.println("SecureQR과 맞지 않는 속성 값");
            return false;
        }
    }

    /**
     * 암호화 된 데이터를 복호화 시킨 값이 원본 데이터와 같다면 원본 데이터를 반환하는 함수
     * @param encrypted 서버로 넘어오는 암호화 된 값
     * @param index SecureQR 에 있는 인덱스 값
     * @return 원본 데이터 String 또는 에러 String
     */
    public String getOriginData(String encrypted, int index) throws Exception {
        String decrypted = arr.getCrypto(index).decrypt(encrypted);
        String[] splitDecrypted = decrypted.split(";;");

        if(splitDecrypted.length != 2) {
            return DATA_ERROR;
        }

        String originData = splitDecrypted[0];
        String hashData = splitDecrypted[1];

        if(!originData.equals(this.data)) {
            return DATA_ERROR;
        }
        if(!hashData.equals(arr.getHash(index).hashing(data))) {
            return HASH_ERROR;
        }
        return originData;
    }
}