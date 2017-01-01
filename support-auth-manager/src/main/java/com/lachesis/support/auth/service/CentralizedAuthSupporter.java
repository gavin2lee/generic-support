package com.lachesis.support.auth.service;

import com.lachesis.support.auth.vo.AuthenticationResult;
import com.lachesis.support.auth.vo.AuthorizationResult;

public interface CentralizedAuthSupporter {
	AuthenticationResult authenticate(String username, String password, String terminalIpAddress);
	AuthorizationResult authorize(String token, String terminalIpAddress);
	void logout(String token);
}
