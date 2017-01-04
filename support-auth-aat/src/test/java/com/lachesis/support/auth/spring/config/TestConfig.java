package com.lachesis.support.auth.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages={"com.lachesis.support.auth.*"})
@PropertySources({ @PropertySource(value="file:${user.home}/generic-support/support-auth-aat-global.properties",ignoreResourceNotFound=true) })
public class TestConfig {
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
}
