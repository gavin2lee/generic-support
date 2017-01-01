package com.lachesis.support.auth.api.handler;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.lachesis.support.auth.api.exception.AuthenticationException;

public class LocalResponseStatusExceptionResolver extends AbstractHandlerExceptionResolver {
	private static final Logger LOG = LoggerFactory.getLogger(LocalResponseStatusExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		response.setContentType("application/json; charset=utf-8");
		try {
			if (ex instanceof AuthenticationException) {
				if(LOG.isDebugEnabled()){
					LOG.debug(String.format("errors : %s", ex.getMessage()));
				}
				AuthenticationException authEx = (AuthenticationException) ex;

				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				PrintWriter writer = response.getWriter();
				writer.write(convertToJson(authEx));
				writer.close();
			} else {
				if(LOG.isErrorEnabled()){
					LOG.error("errors occured", ex);
				}
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				PrintWriter writer = response.getWriter();
				writer.write(String.format("{\"error\":\"%s\"}", ex.getMessage()));
				writer.close();
			}
		} catch (Exception handlerEx) {
			if (LOG.isWarnEnabled()) {
				LOG.warn("handler errors", handlerEx);
			}
		}

		return new ModelAndView();
	}

	private String convertToJson(AuthenticationException ex) {
		String template = "{\"subcode\":\"%s\",\"submsg\":\"%s\"}";
		String ret = String.format(template, ex.getCode(), ex.getMessage());
		return ret;
	}

}
