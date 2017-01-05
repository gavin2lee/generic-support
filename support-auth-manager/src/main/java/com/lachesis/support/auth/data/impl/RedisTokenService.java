package com.lachesis.support.auth.data.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.generic.support.common.util.CommonBeanUtils;
import com.lachesis.support.auth.data.TokenService;
import com.lachesis.support.objects.entity.auth.Token;

@Service("redisTokenService")
public class RedisTokenService implements TokenService {
	public static final String REDIS_TOKEN_HASH_KEY = "authTokens";
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	
	@PostConstruct
	protected void postConstruct(){
		
	}

	@Override
	public List<Token> findPagedTokens(int pageNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token findByTokenValue(String tokenValue) {
		HashOperations<String,String,String> hashOps = redisTemplate.opsForHash();
		String sJson = hashOps.get(REDIS_TOKEN_HASH_KEY, tokenValue);
		if(sJson == null || sJson.trim().length() < 1){
			return null;
		}
		return CommonBeanUtils.toObject(sJson, Token.class);
	}

	@Override
	public void updateLastModifiedTokens(List<Token> tokensToUpdate) {
		for(Token t : tokensToUpdate){
			updateLastModifiedToken(t);
		}

	}
	
	protected void updateLastModifiedToken(Token token){
		HashOperations<String,String,String> hashOps = redisTemplate.opsForHash();
		
		String sExistingJson = hashOps.get(REDIS_TOKEN_HASH_KEY, token.getTokenValue());
		
		Token existingToken = null;
		if(sExistingJson != null){
			existingToken = CommonBeanUtils.toObject(sExistingJson, Token.class);
		}
		
		if(existingToken != null){
			existingToken.setLastModified(token.getLastModified());
			token = existingToken;
		}
				
		String sJson = CommonBeanUtils.toJson(token);
		if(sJson == null){
			return;
		}
		
		hashOps.put(REDIS_TOKEN_HASH_KEY, token.getTokenValue(), sJson);
	}

	@Override
	public void removeTokens(List<Token> tokensToRemove) {
		for(Token t : tokensToRemove){
			removeToken(t);
		}
	}
	
	protected void removeToken(Token t){
		HashOperations<String,String,String> hashOps = redisTemplate.opsForHash();
		hashOps.delete(REDIS_TOKEN_HASH_KEY, t.getTokenValue());
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
		for(Token t : tokensToAdd){
			addToken(t);
		}

	}
	
	protected void addToken(Token t){
		HashOperations<String,String,String> hashOps = redisTemplate.opsForHash();
		hashOps.put(REDIS_TOKEN_HASH_KEY, t.getTokenValue(), CommonBeanUtils.toJson(t));
	}

}
