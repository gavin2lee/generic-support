package com.generic.support.service;

import com.generic.support.vo.AuthorizationResult;

public interface AuthorizationService {
	AuthorizationResult authorize(String token, String ip);
}
