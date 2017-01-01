package com.lachesis.support.common.util.coder;

import com.lachesis.support.common.util.exception.CryptException;

public interface StringCoder {
	String encode(String plaintext) throws CryptException;
	String decode(String ciphertext) throws CryptException;
	void setEncryptionKey(String key);
}
