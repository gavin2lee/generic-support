package com.lachesis.support.auth.cache;

import com.lachesis.support.objects.entity.auth.Token;

public interface TokenQueueBroker {
	void addPutToken(Token t);

	void addUpdateToken(Token t);

	void addRemoveToken(Token t);

	void addEvictToken(Token t);

	void addExpireToken(Token t);

	Token takePutToken();

	Token takeUpdateToken();

	Token takeRemoveToken();

	Token takeEvictToken();

	Token takeExpireToken();
}
