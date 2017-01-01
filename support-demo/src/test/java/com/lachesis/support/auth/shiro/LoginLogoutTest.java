package com.lachesis.support.auth.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class LoginLogoutTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		
		try{
			subject.login(token);
		}catch(AuthenticationException ae){
			Assert.fail(ae.getMessage());
		}
		
		Assert.assertEquals(true, subject.isAuthenticated());
		
		subject.logout();
	}

}
