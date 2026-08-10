package com.shanyan.spring.boot.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * DES encryption and decryption utility.
 * <p>Provides DES/CBC/PKCS5Padding encryption and decryption methods.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class DES {
	
	public DES()
	{
	}


	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	/**
	 * Encrypts a string using DES/CBC/PKCS5Padding.
	 *
	 * @param encryptString the string to encrypt
	 * @param encryptKey the DES key
	 * @return the Base64-encoded encrypted string
	 * @throws Exception if encryption fails
	 */
	public static String encryptDES(String encryptString, String encryptKey)
			throws Exception {
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
		return Base64.encode(encryptedData);
	}

	/**
	 * Decrypts a Base64-encoded DES-encrypted string.
	 *
	 * @param decryptString the Base64-encoded encrypted string
	 * @param decryptKey the DES key
	 * @return the decrypted string
	 * @throws Exception if decryption fails
	 */
	public static String decryptDES(String decryptString, String decryptKey)
			throws Exception {
		byte[] byteMi = Base64.decode(decryptString);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(byteMi);
		return new String(decryptedData);
	}

}
