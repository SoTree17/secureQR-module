package qr_util;

import java.util.Random;

public class RandomString {
    private static final char[] subset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String getString(int length) {
        Random r = new Random();
        char[] buf = new char[length];
        for (int i = 0; i < buf.length; i++) {
            int index = r.nextInt(subset.length);
            buf[i] = subset[index];
        }
        return new String(buf);
    }
}
