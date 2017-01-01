package com.lachesis.support.auth.context.shiro;

import com.lachesis.support.auth.context.vo.AuthorizationInfoVO;

public final class ThreadLocalAuthContext {
	private ThreadLocalAuthContext(){}
	
	private static final ThreadLocal<AuthorizationInfoVO> authorizations = new ThreadLocal<AuthorizationInfoVO>();
	
	public static void set(AuthorizationInfoVO authorization){
		authorizations.set(authorization);
	}
	
	public static AuthorizationInfoVO get(){
		return authorizations.get();
	}
	
	public static void clear(){
		authorizations.remove();
	}
}
