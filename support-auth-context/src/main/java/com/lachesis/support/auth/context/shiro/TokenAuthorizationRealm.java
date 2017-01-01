package com.lachesis.support.auth.context.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lachesis.support.auth.context.comm.AuthorizationInfoProvider;
import com.lachesis.support.auth.context.vo.AuthorizationInfoVO;

public class TokenAuthorizationRealm extends AuthorizingRealm {
	private static final Logger LOG = LoggerFactory.getLogger(TokenAuthorizationRealm.class);
	
	@Autowired
	private AuthorizationInfoProvider authorizationInfoProvider;
	
	public AuthorizationInfoProvider getAuthorizationInfoProvider() {
		return authorizationInfoProvider;
	}

	public void setAuthorizationInfoProvider(AuthorizationInfoProvider authorizationInfoProvider) {
		this.authorizationInfoProvider = authorizationInfoProvider;
	}

	public boolean supports(AuthenticationToken token) {
		return token instanceof LocalAuthenticationToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		AuthorizationInfoVO info = ThreadLocalAuthContext.get();
		if (info == null) {
			LOG.error("cannot find authorization info.");
			throw new RuntimeException("AuthThreadLocalContext error");
		}
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("current thread:%s", Thread.currentThread().getName()));
			LOG.debug(String.format("found authorization info [%s]", info));
		}

		SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
		sai.addRoles(info.getRoles());
		sai.addStringPermissions(info.getPermissions());
		return sai;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		AuthorizationInfoVO infoVO = ThreadLocalAuthContext.get();
		
		if(infoVO != null){
			ThreadLocalAuthContext.clear();
		}
		
		try {
			infoVO = authorizationInfoProvider.provide(token);
			if (infoVO != null) {
				ThreadLocalAuthContext.set(infoVO);
				return createAuthenticationInfo(token, infoVO);
			}
		} catch (Exception e) {
			throw new AuthenticationException("authentication errors:"+findRootCause(e).getMessage(), e);
		}

		throw new AuthenticationException("authentication failed");
	}
	
	private Throwable findRootCause(Throwable ex){
		if(ex.getCause() == null){
			return ex;
		}
		
		return findRootCause(ex.getCause());
	}
	
	private AuthenticationInfo createAuthenticationInfo(AuthenticationToken token, AuthorizationInfoVO info){
		return new SimpleAuthenticationInfo(info.getUser().getUsername(), token.getCredentials(), getName());
	}

}
