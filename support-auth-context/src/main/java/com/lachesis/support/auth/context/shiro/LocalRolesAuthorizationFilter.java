package com.lachesis.support.auth.context.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

public class LocalRolesAuthorizationFilter extends RolesAuthorizationFilter {
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

		Subject subject = getSubject(request, response);
		if (subject.getPrincipal() == null) {
			saveRequestAndRedirectToLogin(request, response);
		} else {
			String unauthorizedUrl = getUnauthorizedUrl();
			if (StringUtils.hasText(unauthorizedUrl)) {
				WebUtils.issueRedirect(request, response, unauthorizedUrl);
			} else {
				response.setContentType("application/json;charset=utf-8");
				WebUtils.toHttp(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
				WebUtils.toHttp(response).getWriter().write(String.format("{\"msg\":\"%s\"}", "roles not qualified"));
			}
		}
		return false;
	}
}
