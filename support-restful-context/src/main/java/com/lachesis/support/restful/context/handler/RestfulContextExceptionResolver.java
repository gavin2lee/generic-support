package com.lachesis.support.restful.context.handler;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.lachesis.support.restful.context.exception.AuthenticationRestfulContextException;
import com.lachesis.support.restful.context.exception.AuthorizationRestfulContextException;

public class RestfulContextExceptionResolver extends AbstractHandlerExceptionResolver {
	private static final Logger LOG = LoggerFactory.getLogger(RestfulContextExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		response.setContentType("application/json; charset=utf-8");
		try {
			if (ex instanceof AuthenticationRestfulContextException) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			} else if (ex instanceof AuthorizationRestfulContextException) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

			PrintWriter writer = response.getWriter();
			writer.write(String.format("{\"msg\":\"%s\"}", ex.getMessage()));
			writer.close();
		} catch (Exception handlerEx) {
			LOG.warn("errors occured in handler:" + handlerEx.getMessage());
		}

		return new ModelAndView();
	}

}
