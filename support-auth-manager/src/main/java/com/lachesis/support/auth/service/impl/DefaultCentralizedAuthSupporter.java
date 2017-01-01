package com.lachesis.support.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lachesis.support.auth.authentication.AuthenticatorProvider;
import com.lachesis.support.auth.authorization.AuthorizerProvider;
import com.lachesis.support.auth.cache.AuthCacheProvider;
import com.lachesis.support.auth.encryption.EncrypterProvider;
import com.lachesis.support.auth.token.AuthTokenManager;
import com.lachesis.support.auth.token.AuthTokenValueAssembler;
import com.lachesis.support.auth.verifier.TokenVerifier;
import com.lachesis.support.auth.vo.AuthenticationResult;
import com.lachesis.support.auth.vo.AuthorizationResult;
import com.lachesis.support.auth.vo.UserDetails;
import com.lachesis.support.auth.vo.UsernamePasswordToken;
import com.lachesis.support.objects.entity.auth.Token;

@Service("defaultCentralizedAuthSupporter")
public class DefaultCentralizedAuthSupporter extends AbstractCentralizedAuthSupporter {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultCentralizedAuthSupporter.class);
	@Autowired
	private AuthenticatorProvider authenticatorProvider;
	@Autowired
	private AuthorizerProvider authorizerProvider;
	@Autowired
	private EncrypterProvider encryptionProvider;
	@Autowired
	private AuthTokenManager tokenManager;
	@Autowired
	private TokenVerifier tokenVerifier;
	@Autowired
	private AuthCacheProvider authCacheProvider;

	protected AuthenticationResult doAuthenticate(String username, String password, String terminalIpAddress) {
		UsernamePasswordToken userPassToken = new UsernamePasswordToken(username, password);
		UserDetails userDetails = authenticatorProvider.getAuthenticator().authenticate(userPassToken);
		if (userDetails == null) {
			LOG.debug(String.format("failed to authenticate [username:%s]", username));
			return null;
		}

		String plainTokenValue = assemblePlainTokenValue(userDetails, terminalIpAddress);
		String tokenValue = encryptionProvider.getEncrypter().encrypt(plainTokenValue);

		Token token = buildAuthToken(username, password, terminalIpAddress, tokenValue);
		tokenManager.store(token);

		return new AuthenticationResult(tokenValue,userDetails.getId());
	}

	private Token buildAuthToken(String userid, String password, String terminalIpAddress, String tokenValue) {
		return (new AuthTokenGenerator(userid, password, terminalIpAddress, tokenValue).generate());
	}

	private String assemblePlainTokenValue(UserDetails userDetails, String terminalIpAddress) {
		return (new AuthTokenValueAssembler(userDetails.getUsername(), terminalIpAddress).buildTokenValue());
	}

	protected AuthorizationResult doAuthorize(String token, String terminalIpAddress) {
		Token authToken = tokenVerifier.verify(token, terminalIpAddress);
		if (authToken == null) {
			LOG.warn(String.format("token [%s] is invalid or expired", token));
			return null;
		}

		AuthorizationResult authResult = findAuthorizationResultFromCache(authToken);

		if (authResult != null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(String.format("find authorization result from cache [token:%s,ip:%s]", token,
						terminalIpAddress));
			}
			return authResult;
		}

		authResult = authorizerProvider.getAuthorizer().authorize(authToken);
		if (authResult == null) {
			LOG.warn(String.format("authorizing failed for [token:%s,ip:%s]", token, terminalIpAddress));
			return null;
		}
		return authResult;
	}

	protected void cacheAuthorizationResult(Token token, AuthorizationResult authorizationResult) {
		authCacheProvider.getAuthorizationResultCache().put(token.getTokenValue(), authorizationResult);
	}

	protected AuthorizationResult findAuthorizationResultFromCache(Token token) {
		return (AuthorizationResult) authCacheProvider.getAuthorizationResultCache().get(token.getTokenValue());
	}

	@Override
	protected void doLogout(String token) {
		tokenManager.dismiss(token);
	}
}
