package com.lachesis.support.common.util.coder;

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

import com.lachesis.support.common.util.crypt.CryptUtils;
import com.lachesis.support.common.util.exception.CryptException;

public class DesCryptStringCoder implements StringCoder {

	private static final String ENCRYPTION_ALGORITHM_DES = "DES";
	private static final String ENCODING = CryptUtils.INTERNAL_ENCODING;

	private String encryptionKey = "LX123456";

	@Override
	public String encode(String plainText) throws CryptException {
		try {
			return doEncrypt(plainText);
		} catch (Exception e) {
			throw new CryptException(e);
		}
	}

	@Override
	public String decode(String cryptograph) throws CryptException {
		try {
			return doDecrypt(cryptograph);
		} catch (Exception e) {
			throw new CryptException(e);
		}
	}

	private String doDecrypt(String cryptograph)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		byte[] decodedCipherBytes = Base64.decodeBase64(cryptograph);

		byte[] decryptedBytes = deDecryptWithDes(decodedCipherBytes, getEncryptionKey());

		return new String(decryptedBytes, ENCODING);
	}

	private byte[] deDecryptWithDes(byte[] cipherBytes, String encryptionKey)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecureRandom random = new SecureRandom();
		DESKeySpec desKey = new DESKeySpec(encryptionKey.getBytes(ENCODING));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM_DES);
		SecretKey securekey = keyFactory.generateSecret(desKey);
		Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM_DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		byte[] decryptBytes = cipher.doFinal(cipherBytes);

		return decryptBytes;
	}

	private String doEncrypt(String plainText)
			throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		byte[] cipherBytes = doEncryptStringWithDes(plainText, getEncryptionKey());

		String encodedCipherString = Base64.encodeBase64URLSafeString(cipherBytes);

		return encodedCipherString;
	}

	private byte[] doEncryptStringWithDes(String plainText, String encrytionKey)
			throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecureRandom random = new SecureRandom();
		DESKeySpec desKey = new DESKeySpec(encrytionKey.getBytes(ENCODING));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM_DES);

		SecretKey securekey = keyFactory.generateSecret(desKey);

		Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM_DES);

		cipher.init(Cipher.ENCRYPT_MODE, securekey, random);

		byte[] cipherBytes = cipher.doFinal(plainText.getBytes(ENCODING));
		return cipherBytes;
	}

	private String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}
}
