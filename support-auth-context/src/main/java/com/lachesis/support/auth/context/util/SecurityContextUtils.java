package com.lachesis.support.auth.context.util;

import javax.servlet.ServletRequest;

import com.lachesis.support.auth.context.common.AuthContextConstants;
import com.lachesis.support.objects.vo.SecurityContext;

public final class SecurityContextUtils {
	public static SecurityContext getSecurityContext(ServletRequest request){
		if(request == null){
			return null;
		}
		
		return (SecurityContext) request.getAttribute(AuthContextConstants.REQUEST_ATTR_SECURITY_CONTEXT);
	}
	
	public static void setSecurityContext(ServletRequest request, SecurityContext securityContext){
		if(request != null){
			request.setAttribute(AuthContextConstants.REQUEST_ATTR_SECURITY_CONTEXT, securityContext);
		}
	}
	
	private SecurityContextUtils(){}
}
