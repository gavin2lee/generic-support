package com.lachesis.support.auth.encryption;

public interface Encrypter {
	String encrypt(String plainText);
	String decrypt(String cryptograph);
}
