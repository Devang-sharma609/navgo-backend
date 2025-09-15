/**
 * 
 */
package com.navgo.util;

/**
 * @author Akash Bais
 *
 */
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptorDecryptor {

	static final byte[] key = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	public String encryptPassword(String plaintext) throws Exception {
		// encryption algorithm and mode
		String algorithm = "AES";

		SecretKey secretKey = new SecretKeySpec(key, 0, key.length, algorithm);

		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		// Encrypt the data using the cipher object
		byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
		String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);

//		System.out.println("Encrypted data: " + encryptedBase64);
//		System.out.println("Secret key:" + secretKey.getAlgorithm());
//		System.out.println(encryptedBase64.length());
		return encryptedBase64;
	}

	public String decryptPassword(String encryptedBase64) throws Exception {
		//same encryption algorithm and mode used during encryption
		String algorithm = "AES";

		SecretKey secretKey = new SecretKeySpec(key, 0, key.length, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		// Decrypt the data using the cipher object
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBase64);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

//		System.out.println("Decrypted data: " + decryptedText);
		return decryptedText;
	}

}
