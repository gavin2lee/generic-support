package com.lachesis.support.auth.token;

import com.lachesis.support.objects.entity.auth.Token;

public interface AuthTokenManager {
	void store(Token authToken);
	void updateLastModifiedTime(Token authToken);
	Token retrieve(String tokenValue);
	Token dismiss(String tokenValue);
}
