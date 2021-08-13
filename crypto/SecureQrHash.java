import java.security.NoSuchAlgorithmException;

/**
 * QR 코드 해시 인터페이스
 */
public interface SecureQrHash {
    String getHash(String message) throws NoSuchAlgorithmException;

    /**
     * Converts a byte array to a hexadecimal string.
     * @param data byte array to convert
     * @return a hexadecimal string array
     */
    static String byteToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for(byte b : data) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
