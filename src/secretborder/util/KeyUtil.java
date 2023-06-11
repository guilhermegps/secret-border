package secretborder.util;

import secretborder.crypto.Bech32;
import secretborder.crypto.Bech32Prefix;

/**
 * @author guilhermegps
 *
 */
public class KeyUtil {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    
    public static String bytesToBech32(byte[] b, Bech32Prefix prefix) {
    	return Bech32.toBech32(prefix, b);
    }

    public static String bytesToHex(byte[] b) {
        char[] hexChars = new char[b.length * 2];
        for (int j = 0; j < b.length; j++) {
            int v = b[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }

    public static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] buf = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            buf[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return buf;
    }

}
