package com.lachesis.support.auth.data.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.lachesis.support.auth.data.TokenService;
import com.lachesis.support.auth.repository.TokenRepository;
import com.lachesis.support.objects.entity.auth.Token;

@Service("databaseBasedTokenService")
public class DatabaseBasedTokenService implements TokenService {
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseBasedTokenService.class);

	private int defaultMaxMinutesAllowed = 50;

	@Autowired
	private TokenRepository tokenRepo;

	@Override
	public List<Token> findPagedTokens(int pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		List<Token> tokens = tokenRepo.selectPage();

		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("pageNum:%d,selected size:%d", pageNum, tokens.size()));
		}
		return tokens;
	}

	@Override
	public Token findByTokenValue(String tokenValue) {
		return tokenRepo.selectOne(tokenValue);
	}

	@Override
	@Transactional
	public void updateLastModifiedTokens(List<Token> tokensToUpdate) {
		if (tokensToUpdate.size() > MAX_SIZE_IN_BATCH) {
			throw new IllegalArgumentException();
		}
		List<Token> strippedTokens = stripTokens(tokensToUpdate);

		for (Token t : strippedTokens) {
			tokenRepo.updateOne(t);
		}

		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("%s tokens had been modified by %s.", strippedTokens.size(), getCurrentThreadName()));
		}
	}

	@Override
	@Transactional
	public void removeTokens(List<Token> tokensToRemove) {
		for (Token t : tokensToRemove) {
			tokenRepo.deleteOne(t.getTokenValue());
		}

		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("%s tokens removed by %s", tokensToRemove.size(), getCurrentThreadName()));
		}
	}

	@Override
	@Transactional
	public void addTokens(List<Token> tokensToAdd) {
		int ret = tokenRepo.insertBatch(tokensToAdd);

		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("%s tokens added by %s", ret, getCurrentThreadName()));
		}

	}
	
	protected String getCurrentThreadName(){
		return Thread.currentThread().getName();
	}

	protected List<Token> stripTokens(List<Token> richTokens) {
		List<Token> tokens = new LinkedList<Token>();
		for (Token t : richTokens) {
			Token tNew = new Token();
			tNew.setLastModified(t.getLastModified());
			tNew.setTokenValue(t.getTokenValue());

			tokens.add(tNew);
		}

		return tokens;
	}

	@Override
	@Transactional
	public void removeExpiredTokens(int maxMinutesAllowed) {
		int ret = tokenRepo.deleteExpiredInBatch(calculateExpireTime(maxMinutesAllowed));

		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("%d tokens expired in %d minutes and removed by %s.", ret, maxMinutesAllowed, getCurrentThreadName()));
		}
	}

	@Override
	public int countExpiredTokens(int maxMinutesAllowed) {
		return tokenRepo.countExpired(calculateExpireTime(maxMinutesAllowed));
	}
	
	private Date calculateExpireTime(int maxMinutesAllowed){
		if ((maxMinutesAllowed < 1) || (maxMinutesAllowed > Integer.MAX_VALUE)) {
			maxMinutesAllowed = defaultMaxMinutesAllowed;
		}

		DateTime expireTime = DateTime.now().minusMinutes(maxMinutesAllowed);
		return expireTime.toDate();
	}

}
