package com.lachesis.support.auth.verifier.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.encryption.EncrypterProvider;
import com.lachesis.support.auth.token.AuthTokenValueParser;
import com.lachesis.support.objects.entity.auth.Token;

@Service("ipAndMaxIdleTimeTokenVerifyingStrategy")
public class IpAndMaxIdleTimeTokenVerifyingStrategy extends AbstractMaxIdleTimeTokenVerifyingStrategy {
	private static final Logger LOG = LoggerFactory.getLogger(IpAndMaxIdleTimeTokenVerifyingStrategy.class);
	
	@Autowired
	private EncrypterProvider encryptionProvider;

	protected Token doVerify(String token, String terminalIpAddress){
		String plainTokenValue = encryptionProvider.getEncrypter().decrypt(token);
		String parsedIp = new AuthTokenValueParser(plainTokenValue).getTerminalIpAddress();
		
		if(!parsedIp.equals(terminalIpAddress)){
			LOG.debug(String.format("[parsedIP:%s and IP:%s] mismatched", parsedIp, terminalIpAddress));
			return null;
		}
		
		return super.doVerify(plainTokenValue, terminalIpAddress);
	}
	
}
