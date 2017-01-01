package com.lachesis.support.auth.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({ @PropertySource(value="${user.home}/aat-test.properties",ignoreResourceNotFound=true) })
public class TestConfig {
	
}
