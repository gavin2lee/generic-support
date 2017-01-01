package com.lachesis.support.auth.context.wrapper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpHeaders;

public class HttpServletRequestWrapper {
	private HttpServletRequest request;

	public HttpServletRequestWrapper(ServletRequest request) {
		if(request instanceof HttpServletRequest){
			this.request = (HttpServletRequest) request;
		}else{
			throw new IllegalArgumentException("cannot indentify such type:"+request.getClass().getName());
		}
	}

	public HttpServletRequestWrapper(HttpServletRequest request) {
		super();
		this.request = request;
	}

	public String getAuthorizationToken() {
		String authrization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authrization == null) {
			throw new AuthenticationException("authorization header [Authorization token ###] missing");
		}

		String token = authrization.substring(6);
		return token;
	}
	
	public String getRemoteIpAddress(){
		return request.getRemoteAddr();
	}
}
