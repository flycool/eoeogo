package diy.eoego.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	
	private static MessageDigest sDigest;
	
	static {
		try {
			sDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	final public static String encode(String source) {
		byte[] encodedBytes = sDigest.digest(source.getBytes());
		return new String(encodedBytes);
	}
	
}
