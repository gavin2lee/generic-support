package com.lachesis.support.common.util.digest;

import com.lachesis.support.common.util.exception.CryptException;

public interface MessageDigester {
	String digestAndHex(String content) throws CryptException;
}
