package com.xworkz.vaccination.util;

import java.security.spec.KeySpec;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptorAndDecryptor {
	
	private static final Logger log = Logger.getLogger(PasswordEncryptorAndDecryptor.class);
	

	private static final String UNICODE_FORMAT = "UTF8";
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private KeySpec ks;
	private SecretKeyFactory skf;
	private Cipher cipher;
	byte[] arrayBytes;
	private String myEncryptionKey;
	private String myEncryptionScheme;
	SecretKey key;

	public PasswordEncryptorAndDecryptor() throws Exception {
		log.info("Invoked PasswordEncryptorAndDecryptor");
		myEncryptionKey = "ThisIsSpartaThisIsSparta";
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		ks = new DESedeKeySpec(arrayBytes);
		skf = SecretKeyFactory.getInstance(myEncryptionScheme);
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = skf.generateSecret(ks);
	}

	public String encrypt(String unencryptedString) {
		log.info("Invoked encrypt");
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			Encoder encoder = java.util.Base64.getEncoder();
			encryptedString = new String(encoder.encodeToString(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	public String decrypt(String encryptedString) {
		log.info("Invoked decrypt");
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			Decoder decoder = java.util.Base64.getDecoder();
			byte[] encryptedText = decoder.decode(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}
}