package com.lachesis.support.common.util.crypt;

import static org.junit.Assert.fail;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lachesis.support.common.util.exception.CryptException;


public class CryptUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEncryptAndDecrypt() {
		String plaintext = "abc123好";
		
		try {
			String ciphertext = CryptUtils.encrypt(plaintext);
			
			Assert.assertThat(ciphertext, Matchers.notNullValue());
			String decryptedPlaintext = CryptUtils.decrypt(ciphertext);
			
			Assert.assertThat(decryptedPlaintext, Matchers.equalTo(plaintext));
		} catch (CryptException e) {
			Assert.fail();
		}
	}

	@Test
	public void testDigest() {
		String contentA = "abc123好";
		String contentB = "abc123好";
		
		try {
			String digestA = CryptUtils.digest(contentA);
			String digestB = CryptUtils.digest(contentB);
			
			Assert.assertThat(digestA, Matchers.equalTo(digestB));
		} catch (CryptException e) {
			fail();
		}
		
	}

}
