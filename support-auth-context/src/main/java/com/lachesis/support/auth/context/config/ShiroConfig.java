package com.lachesis.support.auth.context.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractFilter;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

import com.lachesis.support.auth.context.shiro.LocalPermissionsAuthorizationFilter;
import com.lachesis.support.auth.context.shiro.LocalRolesAuthorizationFilter;
import com.lachesis.support.auth.context.shiro.TokenAuthorizationAccessControlFilter;
import com.lachesis.support.auth.context.shiro.TokenAuthorizationRealm;
import com.lachesis.support.auth.context.shiro.TokenAuthorizationWebSubjectFactory;

@Configuration
public class ShiroConfig {
	
	@Bean(name="tokenAuthorizationRealm")
	public TokenAuthorizationRealm authorizationRealm(){
		TokenAuthorizationRealm realm = new TokenAuthorizationRealm();
		realm.setCachingEnabled(false);
		return realm;
	}
	
	@Bean(name="tokenAuthorizationWebSubjectFactory")
	public TokenAuthorizationWebSubjectFactory authorizationWebSubjectFactory(){
		return new TokenAuthorizationWebSubjectFactory();
	}
	
	@Bean(name="sessionManager")
	public SessionManager sessionManager(){
		DefaultSessionManager sessionManager = new DefaultSessionManager();
		sessionManager.setSessionValidationSchedulerEnabled(false);
		return sessionManager;
	}
	
	@Bean(name="lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}
	
	@Bean(name="tokenAccessControlFilter")
	public TokenAuthorizationAccessControlFilter tokenAuthorizationAccessControlFilter(){
		return new TokenAuthorizationAccessControlFilter();
	}
	
	@Bean(name="localRolesAuthorizationFilter")
	public RolesAuthorizationFilter localRolesAuthorizationFilter(){
		return new LocalRolesAuthorizationFilter();
	}
	
	@Bean(name="localPermissionsAuthorizationFilter")
	public PermissionsAuthorizationFilter localPermissionsAuthorizationFilter(){
		return new LocalPermissionsAuthorizationFilter();
	}
	
	@Bean(name="securityManager")
	@Scope("singleton")
	public org.apache.shiro.mgt.SecurityManager securityManager(){
		DefaultWebSecurityManager mgt = new DefaultWebSecurityManager();
		mgt.setRealm(authorizationRealm());
		DefaultSubjectDAO dao = (DefaultSubjectDAO)mgt.getSubjectDAO();
		DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator)dao.getSessionStorageEvaluator();
		evaluator.setSessionStorageEnabled(false);
		
		mgt.setSubjectFactory(authorizationWebSubjectFactory());
		mgt.setSessionManager(sessionManager());
		return mgt;
	}
	
	@Bean(name="methodInvokingFactoryBean")
	public MethodInvokingFactoryBean methodInvokingFactoryBean(){
		MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
		bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		org.apache.shiro.mgt.SecurityManager mgt = securityManager();
		bean.setArguments(new Object[]{mgt});
		
		return bean;
	}
	
	@Bean(name="defaultAdvisorAutoProxyCreator")
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
		return new DefaultAdvisorAutoProxyCreator();
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		org.apache.shiro.mgt.SecurityManager mgt = securityManager();
		advisor.setSecurityManager(mgt);
		return advisor;
	}
	
	@Bean(name="predefinedFilters")
	public Map<String,AbstractFilter> predefinedFilters(){
		Map<String,AbstractFilter> filters = new HashMap<String,AbstractFilter>();
		filters.put("authcToken", tokenAuthorizationAccessControlFilter());
		filters.put("roles", localRolesAuthorizationFilter());
		filters.put("perms", localPermissionsAuthorizationFilter());
		
		return filters;
	}
}
