package qr.authentication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import crypto.SecureQrCryptoArray;

public class AuthQR {
    public SecureQrCryptoArray arr;

    public final static String DATA_ERROR = "원본 데이터와 다름";
    public final static String HASH_ERROR = "해시 값이 다름";

    /**
     * SecureQR 코드 인증에 필요한 값 초기화
     *
     * @param arr 암호화 배열
     */
    public AuthQR(SecureQrCryptoArray arr) {
        this.arr = arr;
    }

    /**
     * 올바른 SecureQR 인지 체크하는 함수
     * @param read_data SecureQR 을 읽은 데이터
     * @return SecureQR 이면 True 반환
     */
    public static boolean isSecureQR(String read_data) {
        try {
            JsonObject obj = new Gson().fromJson(read_data, JsonObject.class);
            int c_index = obj.get("c_index").getAsInt();
            int d_index = obj.get("d_index").getAsInt();
            String data = obj.get("data").getAsString();

            return true;
        }
        catch (JsonSyntaxException e) {
            System.out.println("올바르지 않은 Json 문법");
            e.printStackTrace();
            return false;
        }
        catch (NullPointerException e) {
            System.out.println("SecureQR과 맞지 않는 속성 값");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 암호화 된 데이터를 복호화 시킨 값이 원본 데이터와 같다면 원본 데이터를 반환하는 함수
     * @param encrypted 서버로 넘어오는 암호화 된 값
     * @param c_index 암호화 인덱스값
     * @param d_index 데이터 인덱스값
     * @return 원본 데이터 String 또는 에러 String
     */
    public String getOriginData(String encrypted, int c_index, int d_index) throws Exception {
        String data = arr.getData(d_index);
        String decrypted = arr.getCrypto(c_index).decrypt(encrypted);
        String[] splitDecrypted = decrypted.split(";;");

        if(splitDecrypted.length != 2) {
            return DATA_ERROR;
        }

        String originData = splitDecrypted[0];
        String hashData = splitDecrypted[1];

        if(!originData.equals(data)) {
            return DATA_ERROR;
        }
        if(!hashData.equals(arr.getHash(c_index).hashing(data))) {
            return HASH_ERROR;
        }
        return originData;
    }
}