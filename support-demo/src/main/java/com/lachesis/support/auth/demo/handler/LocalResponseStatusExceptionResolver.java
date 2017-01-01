package com.lachesis.support.auth.demo.handler;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

public class LocalResponseStatusExceptionResolver extends AbstractHandlerExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		response.setContentType("application/json; charset=utf-8");
		try {
			if (ex instanceof AuthenticationException) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			} else if (ex instanceof AuthorizationException) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			
			PrintWriter writer = response.getWriter();
			writer.write(String.format("{\"msg\":\"%s\"}", ex.getMessage()));
			writer.close();
		} catch (Exception handlerEx) {
			
		} 

		return new ModelAndView();
	}

}
