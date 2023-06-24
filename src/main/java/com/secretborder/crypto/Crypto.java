package com.secretborder.crypto;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;

import com.secretborder.util.KeyUtil;

public class Crypto {

	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public static byte[] generatePrivateKey(byte[] seed) {
        try {
        	var sRandom = SecureRandom.getInstanceStrong();
        	if (seed!=null)
        		sRandom.setSeed(seed);
        	Security.addProvider(new BouncyCastleProvider());	
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("ECDSA", "BC");
            kpg.initialize(new ECGenParameterSpec("secp256k1"), sRandom);
            KeyPair processorKeyPair = kpg.genKeyPair();
            
            return bytesFromBigInteger(((ECPrivateKey) processorKeyPair.getPrivate()).getS());
        
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
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

    public static byte[] genPubKey(byte[] secKey) {
        BigInteger x = KeyUtil.bigIntFromBytes(secKey);
        if (!(BigInteger.ONE.compareTo(x) <= 0 && x.compareTo(Point.getn().subtract(BigInteger.ONE)) <= 0)) {
            throw new RuntimeException("The secret key must be an integer in the range 1..n-1.");
        }
        Point ret = Point.mul(Point.G, x);
        return Point.bytesFromPoint(ret);
    }

	public static byte[] encrypt(byte[] input, String password) {
		try {
			var salt = generateSalt();
			var key = deriveKeyFromPassword(password, salt, 32);
			var iv = deriveKeyFromPassword(password, key.getEncoded(), 16).getEncoded();
			
			byte[] outputBytes = encrypt(input, key, new IvParameterSpec(iv));
			return Arrays.concatenate(salt, outputBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] decrypt(byte[] cipherBytes, String password) throws BadPaddingException {
		try {
			var salt = Arrays.copyOfRange(cipherBytes, 0, 16);
			var key = deriveKeyFromPassword(password, salt, 32);
			var iv = deriveKeyFromPassword(password, key.getEncoded(), 16).getEncoded();
			
			return decrypt(Arrays.copyOfRange(cipherBytes, 16, cipherBytes.length), key, new IvParameterSpec(iv));
		} catch (BadPaddingException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    private static byte[] encrypt(byte[] input, SecretKey key, IvParameterSpec iv)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		return cipher.doFinal(input);
	}

	private static byte[] decrypt(byte[] cipherBytes, SecretKey key, IvParameterSpec iv)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		return cipher.doFinal(cipherBytes);
	}

	private static SecretKey deriveKeyFromPassword(String password, byte[] salt, int keySize)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 100000, keySize*8);
		SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
		return secret;
	}

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
