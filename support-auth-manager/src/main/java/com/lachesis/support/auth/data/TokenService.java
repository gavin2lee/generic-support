package com.lachesis.support.auth.data;

import java.util.List;

import com.lachesis.support.objects.entity.auth.Token;

public interface TokenService {
	int PAGE_SIZE = 300;
	int MAX_SIZE_IN_BATCH = 300;
	List<Token> findPagedTokens(int pageNum);
	Token findByTokenValue(String tokenValue);
	void updateLastModifiedTokens(List<Token> tokensToUpdate);
	void removeTokens(List<Token> tokensToRemove);
	void removeExpiredTokens(int maxMinutesAllowed);
	int countExpiredTokens(int maxMinutesAllowed);
	void addTokens(List<Token> tokensToAdd);
}
