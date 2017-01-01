package com.lachesis.support.auth.context.comm.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.lachesis.support.auth.context.comm.AuthCenterClient;
import com.lachesis.support.auth.context.comm.AuthorizationException;
import com.lachesis.support.auth.context.comm.AuthorizationInfoProvider;
import com.lachesis.support.auth.context.vo.AuthorizationInfoVO;
import com.lachesis.support.auth.context.vo.UserDetailVO;
import com.lachesis.support.objects.vo.auth.AuthorizationResponseVO;

@Component("restTemplateAuthorizationInfoProvider")
public class RestTemplateAuthorizationInfoProvider implements AuthCenterClient, AuthorizationInfoProvider {
	private static final Logger LOG = LoggerFactory.getLogger(RestTemplateAuthorizationInfoProvider.class);
	@Autowired
	private RestTemplate restTemplate;

	@Value("${support.auth.context.authc.authorize.url}")
	private String authorizingUrl;
	
	@PostConstruct
	public void afterPropertySet(){
		if(restTemplate == null){
			LOG.error("RestTemplate must be provided");
			throw new RuntimeException("RestTemplate must be provided.");
		}
		
		if(StringUtils.isBlank(authorizingUrl)){
			LOG.error("authorization server URL must be specified.");
			throw new RuntimeException("authorization server URL must be specified.");
		}
		
		if(LOG.isInfoEnabled()){
			LOG.info(String.format("current authorization server URL:%s", authorizingUrl));
		}
	}

	@Override
	public AuthorizationResponseVO authorize(String token, String terminalIpAddress) throws AuthorizationException{
		return internalAuthorize(token,terminalIpAddress);
	}

	@Override
	public AuthorizationInfoVO provide(AuthenticationToken token) throws AuthorizationException{
		if(token == null){
			LOG.error("token must be specified.");
			throw new RuntimeException();
		}
		AuthorizationResponseVO resp = internalAuthorize((String)token.getPrincipal(), (String)token.getCredentials());
		if(resp == null){
			throw new AuthorizationException("Cannot normally gained authorization infomation.");
		}
		UserDetailVO user = new UserDetailVO(resp.getUserId(), resp.getUsername());
		
		return new AuthorizationInfoVO(user, resp.getRoles(), resp.getPermissions());
	}
	
	protected AuthorizationResponseVO internalAuthorize(String token, String terminalIpAddress) throws AuthorizationException{
		if (LOG.isDebugEnabled()) {
			LOG.debug("authorization api:" + authorizingUrl);
			LOG.debug(String.format("authorize for [token:%s,ip:%s]", token, terminalIpAddress));
		}
		String url = String.format("%s/%s?ip=%s", authorizingUrl, token, terminalIpAddress);

		AuthorizationResponseVO resp = null;

		try {
			resp = restTemplate.getForObject(url, AuthorizationResponseVO.class);
		}catch(ResourceAccessException rae){
			LOG.error("connection errors:"+rae.getMessage());
			throw new AuthorizationException(rae);
		} catch (Exception e) {
			LOG.warn("errors while communicating with auth center:"+e.getMessage());
			throw new AuthorizationException(e);
		}
		
		if(resp == null){
			throw new AuthorizationException();
		}
		
		if(LOG.isDebugEnabled()){
			LOG.debug(String.format("%s", resp.toString()));
		}
		return resp;
	}
}
