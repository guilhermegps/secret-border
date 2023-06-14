package secretborder.crypto;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import secretborder.util.KeyUtil;

public class Crypto {

	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public static byte[] generatePrivateKey() {
        try {
        	Security.addProvider(new BouncyCastleProvider());	
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("ECDSA", "BC");
            kpg.initialize(new ECGenParameterSpec("secp256k1"), new SecureRandom());
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

}
