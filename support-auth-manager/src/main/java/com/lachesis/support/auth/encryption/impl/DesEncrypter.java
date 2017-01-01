package com.lachesis.support.auth.encryption.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.encryption.Encrypter;

public class DesEncrypter implements Encrypter {
	private static final Logger LOG = LoggerFactory.getLogger(DesEncrypter.class);
	
	private static final String ENCRYPTION_ALGORITHM_DES = "DES";
	private static final String ENCODING  = "UTF-8";
		
	private String encryptionKey = "LX123456";

	@Override
	public String encrypt(String plainText) {
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("encrypting %s", plainText));
		}
		
		if(StringUtils.isBlank(plainText)){
			return null;
		}
		
		return doEncrypt(plainText);
	}

	@Override
	public String decrypt(String cryptograph) {
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("decrypting %s", cryptograph));
		}
		
		if(StringUtils.isBlank(cryptograph)){
			return null;
		}
		
		return doDecrypt(cryptograph);
	}
	
	private String doDecrypt(String cryptograph){
		byte[] decodedCipherBytes = Base64.decodeBase64(cryptograph);
		if(decodedCipherBytes == null){
			LOG.warn("decoding failed");
			return null;
		}
		
		byte[] decryptedBytes;
		try {
			decryptedBytes = deDecryptWithDes(decodedCipherBytes, getEncryptionKey());
		} catch (Exception e) {
			LOG.warn("decryption error.", e);
			decryptedBytes = null;
		}
		
		if(decryptedBytes == null){
			LOG.warn("decrypting failed");
			return null;
		}
		
		String plainText = null;
		try {
			plainText =  new String(decryptedBytes, ENCODING);
		} catch (UnsupportedEncodingException e) {
			LOG.warn("error while creating plain text", e);
		}
		
		return plainText;
	}
	
	private byte[] deDecryptWithDes(byte[] cipherBytes, String encryptionKey) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		SecureRandom random = new SecureRandom();
		DESKeySpec desKey = new DESKeySpec(encryptionKey.getBytes(ENCODING));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM_DES);
		SecretKey securekey = keyFactory.generateSecret(desKey);
		Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM_DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		byte[] decryptBytes = cipher.doFinal(cipherBytes);
		
		return decryptBytes;
	}
	
	private String doEncrypt(String plainText){
		byte[] cipherBytes;
		try {
			cipherBytes = doEncryptStringWithDes(plainText, getEncryptionKey());
		} catch (Exception e) {
			LOG.warn("error while encrypting.", e);
			cipherBytes = null;
		}
		
		if(cipherBytes == null){
			LOG.warn("encrypting failed");
			return null;
		}
		
		String encodedCipherString = Base64.encodeBase64URLSafeString(cipherBytes);
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("encryption result:%s",encodedCipherString));
		}
		return encodedCipherString;
	}
	
	private byte[] doEncryptStringWithDes(String plainText, String encrytionKey) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		SecureRandom random = new SecureRandom();
		DESKeySpec desKey = new DESKeySpec(encrytionKey.getBytes(ENCODING));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM_DES);
		
		SecretKey securekey = keyFactory.generateSecret(desKey);
		
		Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM_DES);
		
		cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
		
		byte[] cipherBytes = cipher.doFinal(plainText.getBytes(ENCODING));
		return cipherBytes;
	}
	
	private String getEncryptionKey(){
		return encryptionKey;
	}
}
