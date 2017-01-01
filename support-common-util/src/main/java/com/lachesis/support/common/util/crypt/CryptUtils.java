package com.lachesis.support.common.util.crypt;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.lachesis.support.common.util.coder.DesCryptStringCoder;
import com.lachesis.support.common.util.coder.StringCoder;
import com.lachesis.support.common.util.digest.Md5BasedMessageDigester;
import com.lachesis.support.common.util.digest.MessageDigester;
import com.lachesis.support.common.util.exception.CryptException;
import com.lachesis.support.common.util.text.TextUtils;

public class CryptUtils {
	public static final String DES_CRYPT_KEY_FILE = "des.crypt.key";
	public static final String INTERNAL_ENCODING = "UTF-8";
	public static final String DYNAMIC_PARAM_KEY_DIGESTER = "support.crypt.digester";
	public static final String DYNAMIC_PARAM_KEY_CODER = "support.crypt.coder";

	private static final String DEFAULT_DES_CRYPT_KEY = "LX123456";

	private static final String DEFAULT_DIGESTER = Md5BasedMessageDigester.class.getName();
	private static final String DEFAULT_CODER = DesCryptStringCoder.class.getName();
	private static MessageDigester digester;
	private static StringCoder coder;

	private static String desCryptKey;

	static {
		initDesCryptKey();
		init();
		validate();
	}

	public static String encrypt(String plaintext) throws CryptException {
		return coder.encode(plaintext);
	}

	public static String decrypt(String ciphertext) throws CryptException {
		return coder.decode(ciphertext);
	}

	public static String digest(String content) throws CryptException {
		return digester.digestAndHex(content);
	}

	private static void init() {
		try {
			initializeDigester();
			initializeCoder();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void initializeDigester()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String concreteDigesterType = System.getProperty(DYNAMIC_PARAM_KEY_DIGESTER, DEFAULT_DIGESTER);
		if (TextUtils.isNotBlank(concreteDigesterType)) {
			Class<?> initDigester = Class.forName(concreteDigesterType);
			if (!MessageDigester.class.isAssignableFrom(initDigester)) {
				throw new RuntimeException("the digester type provided is not appropriate.");
			}
			digester = (MessageDigester) initDigester.newInstance();
		}
	}

	private static void initializeCoder()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String concreteCoder = System.getProperty(DYNAMIC_PARAM_KEY_CODER, DEFAULT_CODER);
		if (TextUtils.isNotBlank(concreteCoder)) {
			Class<?> initCoder = Class.forName(concreteCoder);
			if (!StringCoder.class.isAssignableFrom(initCoder)) {
				throw new RuntimeException("the coder type provided is not appropraite.");
			}
			coder = (StringCoder) initCoder.newInstance();

			if (TextUtils.isNotBlank(desCryptKey)) {
				coder.setEncryptionKey(desCryptKey);
			}
		}
	}

	private static void validate() {
		if (digester == null) {
			throw new IllegalStateException("digester must be specified");
		}
		if (coder == null) {
			throw new IllegalStateException("coder must be specified");
		}
	}

	private static void initDesCryptKey() {
		InputStream is = null;
		try {
			is = CryptUtils.class.getClassLoader().getResourceAsStream(DES_CRYPT_KEY_FILE);
			String key = IOUtils.toString(is, INTERNAL_ENCODING);
			if (TextUtils.isNotBlank(key)) {
				desCryptKey = key;
			}
		} catch (IOException e) {

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}

		if (TextUtils.isBlank(desCryptKey)) {
			desCryptKey = DEFAULT_DES_CRYPT_KEY;
		}
	}
}
