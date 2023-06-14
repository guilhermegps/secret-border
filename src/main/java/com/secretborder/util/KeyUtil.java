package com.secretborder.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.secretborder.crypto.Bech32;
import com.secretborder.crypto.Bech32Prefix;

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

    public static byte[] bytesFromBigInteger(BigInteger n) {

        byte[] b = n.toByteArray();

        if (b.length == 32) {
            return b;
        } else if (b.length > 32) {
            return Arrays.copyOfRange(b, b.length - 32, b.length);
        } else {
            byte[] buf = new byte[32];
            System.arraycopy(b, 0, buf, buf.length - b.length, b.length);
            return buf;
        }
    }

    public static BigInteger bigIntFromBytes(byte[] b) {
        return new BigInteger(1, b);
    }

    public static byte[] sha256(byte[] b) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(b);
    }

}
