package com.lachesis.support.auth.cache;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.data.TokenService;
import com.lachesis.support.objects.entity.auth.Token;

public abstract class AbstractTokenSynchronizer implements TokenSynchronizer {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractTokenSynchronizer.class);
	private TokenQueueBroker tokenQueueBroker;
	private TokenService tokenService;
	private int waitPeriodMilliSeconds = 1000*10;
	private int maxSizeInPatch = 100;

	public AbstractTokenSynchronizer(TokenQueueBroker tokenQueueBroker, TokenService tokenService) {
		super();
		this.tokenQueueBroker = tokenQueueBroker;
		this.tokenService = tokenService;
	}

	protected TokenQueueBroker getTokenQueueBroker() {
		return tokenQueueBroker;
	}

	protected TokenService getTokenService() {
		return tokenService;
	}

	protected boolean needToWait(long st, long ed, int size) {
		if (size == 0) {
			return true;
		}

		long elapse = (ed - st);
		if (elapse >= waitPeriodMilliSeconds) {
			return false;
		}

		if (size >= maxSizeInPatch) {
			return false;
		}

		return true;
	}

	protected int getMaxSizeInPatch() {
		return maxSizeInPatch;
	}

	public void setMaxSizeInPatch(int maxSizeInPatch) {
		this.maxSizeInPatch = maxSizeInPatch;
	}

	@Override
	public void run() {
		while (true) {
			try {
				long st = System.currentTimeMillis();
				List<Token> tokens = new ArrayList<Token>(getMaxSizeInPatch());
				while (true) {
					if (!needToWait(st, System.currentTimeMillis(), tokens.size())) {
						List<Token> tokensCopy = new LinkedList<Token>(tokens);
						processWithTokenService(tokensCopy);

						tokens.clear();
						st = System.currentTimeMillis();
					}

					Token t = takeToken();
					if (t != null) {
						tokens.add(t);
					}
				}

			} catch (Exception e) {
				LOG.warn(this.getClass().getSimpleName() + " - " + e.getMessage(),e);
			}

		}
	}

	protected abstract void processWithTokenService(List<Token> tokensCopy);

	protected abstract Token takeToken();

}
