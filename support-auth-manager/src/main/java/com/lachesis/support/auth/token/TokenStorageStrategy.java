package com.lachesis.support.auth.token;

import com.lachesis.support.objects.entity.auth.Token;

public interface TokenStorageStrategy {
	void save(Token authToken);
	void update(Token authToken);
	Token find(String tokenValue);
	Token remove(String tokenValue);
}
