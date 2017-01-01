package com.lachesis.support.common.util.digest;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import com.lachesis.support.common.util.crypt.CryptUtils;
import com.lachesis.support.common.util.exception.CryptException;

public class Md5BasedMessageDigester implements MessageDigester {

	@Override
	public String digestAndHex(String content) throws CryptException {
		try {
			MessageDigest md = DigestUtils.getMd5Digest();
			byte[] digests = md.digest(content.getBytes(CryptUtils.INTERNAL_ENCODING));
			return Hex.encodeHexString(digests);
		} catch (Exception e) {
			throw new CryptException(e);
		}
	}

}
