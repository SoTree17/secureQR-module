package crypto;

import qr_util.MethodPair;
import java.util.ArrayList;


public class SecureQrCryptoArray {
    private ArrayList<MethodPair> crypto_arr;
    private ArrayList<String> data_arr;

    public SecureQrCryptoArray() {
        this.crypto_arr = new ArrayList<>();
        this.data_arr = new ArrayList<>();
    }

    public int crypto_size() {
        return crypto_arr.size();
    }

    public int data_size() {
        return data_arr.size();
    }

    public void add(SecureQrHash _h, SecureQrCrypto _c) {
        crypto_arr.add(new MethodPair(_h, _c));
    }

    public SecureQrHash getHash(int index) {
        return crypto_arr.get(index).getHash();
    }

    public SecureQrCrypto getCrypto(int index) {
        return crypto_arr.get(index).getCrypto();
    }

    public String getData(int index) {
        return data_arr.get(index);
    }

    /**
     * 데이터 추가
     * @param _data 추가할 데이터
     * @return 데이터의 인덱스 반환
     */
    public int addData(String _data) {
        data_arr.add(_data);
        return data_arr.size() - 1;
    }

    public SecureQrHash getLastHash() {
        return crypto_arr.get(crypto_arr.size() - 1).getHash();
    }

    public SecureQrCrypto getLastCrypto() {
        return crypto_arr.get(crypto_arr.size() - 1).getCrypto();
    }
}
