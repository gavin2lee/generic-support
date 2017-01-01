package com.lachesis.support.auth.context.shiro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lachesis.support.auth.context.util.SecurityContextUtils;
import com.lachesis.support.auth.context.vo.AuthorizationInfoVO;
import com.lachesis.support.auth.context.wrapper.HttpServletRequestWrapper;
import com.lachesis.support.objects.vo.SecurityContext;

public class TokenAuthorizationAccessControlFilter extends AccessControlFilter {
	private static final Logger LOG = LoggerFactory.getLogger(TokenAuthorizationAccessControlFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		return subject.isAuthenticated();
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		try {
			AuthenticationToken localToken = createToken(request, response);
			Subject subject = getSubject(request, response);
			subject.login(localToken);

			cacheSecurityContext(localToken, request);
		} catch (Exception e) {
			LOG.warn("errors while login:"+e.getMessage());
			onLoginFail(e, response);
			return false;
		}

		return true;

	}

	protected void cacheSecurityContext(AuthenticationToken localToken, ServletRequest request) {
		SecurityContext securityContext = buildSecurityContext(localToken);
		if (LOG.isDebugEnabled()) {
			LOG.debug("cache :"+securityContext.toString());
		}
		
		SecurityContextUtils.setSecurityContext(request, securityContext);;
	}

	protected SecurityContext buildSecurityContext(AuthenticationToken localToken) {
		String principal = null;
		Collection<String> roles = null;
		Collection<String> permissions = null;
		String token = null;
		String terminalIpAddress = null;
		AuthorizationInfoVO authzRespVO = ThreadLocalAuthContext.get();
		if (authzRespVO == null) {
			if (LOG.isWarnEnabled()) {
				LOG.warn("cannot get authorization infomation from thread local auth context.");
			}
		} else {
			principal = authzRespVO.getUser().getUsername();
			roles = authzRespVO.getRoles();
			permissions = authzRespVO.getPermissions();
		}

		if ( (localToken instanceof LocalAuthenticationToken) && (localToken != null) ) {
			token = (String) localToken.getPrincipal();
			terminalIpAddress = (String) localToken.getCredentials();
		}
		
		return new SimpleSecurityContext(principal, token, terminalIpAddress, roles, permissions);
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
		Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());

		return new LocalAuthenticationToken(requestWrapper.getAuthorizationToken(), requestWrapper.getRemoteIpAddress(),
				params);
	}

	protected void onLoginFail(Exception ex, ServletResponse response) throws IOException {
		if (ex instanceof AuthenticationException) {
			if (response instanceof HttpServletResponse) {
				((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} else {
			if (response instanceof HttpServletResponse) {
				((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}

		if (response instanceof HttpServletResponse) {
			response.setContentType("application/json;charset=utf-8");
			PrintWriter writer = ((HttpServletResponse) response).getWriter();
			writer.write(String.format("{\"msg\":\"%s\"}", ex.getMessage()));
		}

	}
}
