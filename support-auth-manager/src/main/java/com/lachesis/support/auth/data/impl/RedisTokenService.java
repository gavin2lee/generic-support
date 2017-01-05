package com.lachesis.support.auth.data.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.data.TokenService;
import com.lachesis.support.objects.entity.auth.Token;

@Service("redisTokenService")
public class RedisTokenService implements TokenService {
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Override
	public List<Token> findPagedTokens(int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token findByTokenValue(String tokenValue) {
		String result = redisTemplate.getExpire(key)
		return null;
	}

	@Override
	public void updateLastModifiedTokens(List<Token> tokensToUpdate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTokens(List<Token> tokensToRemove) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeExpiredTokens(int maxMinutesAllowed) {
		// TODO Auto-generated method stub

	}

	@Override
	public int countExpiredTokens(int maxMinutesAllowed) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addTokens(List<Token> tokensToAdd) {
		// TODO Auto-generated method stub

	}

}
