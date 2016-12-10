package com.cds.utils;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.cds.field.Constants;


public class EncryptionUtils {

	private static final String ALGO = "AES";// AES,AESWrap,ARCFOUR,Blowfish,DES,DESede,DESedeWrap,ECIES,RC2,RC4,RC5,RSA
	private static Cipher c;
	private static Base64.Decoder decoder;
	private static Base64.Encoder encoder;
	private static Charset CHARSET = Charset.forName("UTF-8");

	private static MessageDigest digester;

	static {
		try {
			// init Cipher
			c = Cipher.getInstance(ALGO);
			encoder = Base64.getEncoder();
			decoder = Base64.getDecoder();

			digester = MessageDigest.getInstance("MD5");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String initMasterKey() {
		UUID uuid = UUID.randomUUID();
		// the uuid alway return larger than 16 bytes
		return uuid.toString().replace("-", "").substring(0, 16);
	}

	private static Key generateKey(String value) throws Exception {
		Key key = new SecretKeySpec(value.getBytes(CHARSET), ALGO);
		return key;
	}

	public static String encrypt(String plainText, String key) throws Exception {
		c.init(Cipher.ENCRYPT_MODE, generateKey(key));
		byte[] encVal = c.doFinal(plainText.getBytes(CHARSET));
		String encryptedValue = encoder.encodeToString(encVal);
		return encryptedValue;
	}

	public static String decrypt(String encryptedText, String key) throws Exception {
		c.init(Cipher.DECRYPT_MODE, generateKey(key));
		byte[] decodedValue = decoder.decode(encryptedText);
		byte[] decValue = c.doFinal(decodedValue);
		String decryptedValue = new String(decValue, CHARSET);
		return decryptedValue;
	}

	public static String encryptWithLoadedKey(String plainText) throws Exception {
		if (plainText == null || "".equals(plainText)) {
			return "";
		}
//		return encrypt(plainText, SecurityDAOImpl.getInstance().getEncryptionKey());
		return plainText;
	}

	public static String decryptWithLoadedKey(String encryptedText) throws Exception {
		if (encryptedText == null || "".equals(encryptedText)) {
			return "";
		}
//		return decrypt(encryptedText, SecurityDAOImpl.getInstance().getEncryptionKey());
		return encryptedText;
	}

	public static String md5Hash(String str) {
		try {
			if (str == null)
				return null;
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(EncryptionUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static String standardizePasswordKey(String original) {
		if (original == null) {
			return Constants.EMPTY;
		}

		original = md5Hash(original);
		return original.substring(0, 16);
	}

	public static String encryptURL(String plainText, String key) throws Exception {
		c.init(Cipher.ENCRYPT_MODE, generateKey(key));
		byte[] encVal = c.doFinal(plainText.getBytes(CHARSET));
		String encryptedValue = encoder.encodeToString(encVal);
		encryptedValue = URLEncoder.encode(encryptedValue, "UTF-8");
		return encryptedValue;
	}

	public static String decryptURL(String encryptedText, String key) throws Exception {
		encryptedText = URLDecoder.decode(encryptedText, "UTF-8");
		c.init(Cipher.DECRYPT_MODE, generateKey(key));
		byte[] decodedValue = decoder.decode(encryptedText);
		byte[] decValue = c.doFinal(decodedValue);
		String decryptedValue = new String(decValue, CHARSET);

		return decryptedValue;
	}

	public static String encryptMD5(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("String to encript cannot be null or zero length");
		}
		digester.update(str.getBytes());
		byte[] hash = digester.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			} else {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}
		return hexString.toString();
	}

	public static void main(String[] args) {
		try {
			System.out.println(encryptMD5("@123"));
			System.out.println(decryptWithLoadedKey("865bedd2fba8fe20b828ed07600c64a4"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
