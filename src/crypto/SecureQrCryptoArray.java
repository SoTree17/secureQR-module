package crypto;

import qr_util.CryptoDict;
import qr_util.MethodPair;
import java.util.ArrayList;


public class SecureQrCryptoArray {
    private ArrayList<MethodPair> arr;

    SecureQrCryptoArray() {
        this.arr = new ArrayList<MethodPair>();
    }

    public int size() {
        return arr.size();
    }

    public void add(SecureQrHash _h, SecureQrCrypto _c) {
        arr.add(new MethodPair(_h, _c));
    }

    // 메소드 오버로딩
    public void add(SecureQrHash _h, SecureQrCrypto _c, CryptoDict _d) {
        arr.add(new MethodPair(_h, _c, _d));
    }

    public SecureQrHash getHash(int index) {
        return arr.get(index).getHash();
    }

    public SecureQrCrypto getCrypto(int index) {
        return arr.get(index).getCrypto();
    }

    public MethodPair getPair(int index) {
        return arr.get(index);
    }

    public SecureQrHash getLastHash() {
        return arr.get(arr.size() - 1).getHash();
    }

    public SecureQrCrypto getLastCrypto() {
        return arr.get(arr.size() - 1).getCrypto();
    }

    public MethodPair getLastPair() {
        return arr.get(arr.size() - 1);
    }

}
