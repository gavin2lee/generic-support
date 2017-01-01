package com.lachesis.support.restful.context.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class RestfulDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = -5989229769488678584L;

	@Override
	protected void onRefresh(ApplicationContext context) {
		setDetectAllHandlerMappings(false);
		super.onRefresh(context);
	}

}
