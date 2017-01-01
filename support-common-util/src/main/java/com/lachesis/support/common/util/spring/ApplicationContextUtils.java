package com.lachesis.support.common.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtils implements ApplicationContextAware {
	private static ApplicationContext ctx;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}
	
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException{
		if(ctx == null){
			return null;
		}
		return ctx.getBean(name, requiredType);
	}

}
