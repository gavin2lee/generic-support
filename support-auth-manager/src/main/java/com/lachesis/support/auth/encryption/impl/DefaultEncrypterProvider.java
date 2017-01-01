package com.lachesis.support.auth.encryption.impl;

import org.springframework.stereotype.Service;

import com.lachesis.support.auth.encryption.Encrypter;
import com.lachesis.support.auth.encryption.EncrypterProvider;

@Service("defaultEncrypterProvider")
public class DefaultEncrypterProvider implements EncrypterProvider {

	private Encrypter encrypter = new DesEncrypter();
	
	public void setEncrypter(Encrypter encrypter) {
		this.encrypter = encrypter;
	}
	
	@Override
	public Encrypter getEncrypter() {
		if(this.encrypter == null){
			throw new RuntimeException("the concrete encrypter should be specified");
		}
		return this.encrypter;
	}

}
