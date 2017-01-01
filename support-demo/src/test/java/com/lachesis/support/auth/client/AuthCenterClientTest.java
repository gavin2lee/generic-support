package com.lachesis.support.auth.client;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.lachesis.support.objects.vo.auth.AuthenticationRequestVO;
import com.lachesis.support.objects.vo.auth.AuthenticationResponseVO;
import com.lachesis.support.objects.vo.auth.AuthorizationResponseVO;

import net.sf.json.JSONObject;

@Ignore
public class AuthCenterClientTest {
	static final Logger LOG = LoggerFactory.getLogger(AuthCenterClientTest.class);
	RestTemplate restTemplate;
	Map<String, String> uriVars;
	String baseUrl = "http://127.0.0.1:9090/authc/api/v1";
	String authenticationBaseUrl = baseUrl + "/tokens";
	String authorizationBaseUrl = baseUrl + "/authorizations";
	
	int maxSizeToExecute = 100000;

	@Before
	public void setUp() throws Exception {
		restTemplate = new RestTemplate();
		uriVars = new HashMap<String, String>();
	}

	@Test
	public void testAuthenticate() {
		String username = "283";
		String password = "123";

		String url = authenticationBaseUrl;

		AuthenticationRequestVO vo = new AuthenticationRequestVO(username, password);

		try {
			AuthenticationResponseVO respVO = restTemplate.postForObject(url, prepareHttpEntity(vo),
					AuthenticationResponseVO.class, uriVars);
			Assert.assertThat(respVO, Matchers.notNullValue());
			Assert.assertThat(respVO.getToken(), Matchers.notNullValue());

			LOG.debug("token returned:" + respVO.getToken());
		} catch (Exception e) {
			LOG.error("fail", e);
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testAuthorize() {
		String username = "283";
		String password = "123";

		String url = authenticationBaseUrl;

		AuthenticationRequestVO vo = new AuthenticationRequestVO(username, password);

		try {
			AuthenticationResponseVO authenticationResp = restTemplate.postForObject(url, prepareHttpEntity(vo),
					AuthenticationResponseVO.class, uriVars);
			Assert.assertThat(authenticationResp, Matchers.notNullValue());
			Assert.assertThat(authenticationResp.getToken(), Matchers.notNullValue());

			LOG.debug("token returned:" + authenticationResp.getToken());

			String token = authenticationResp.getToken();
			String ip = "127.0.0.1";
			String authorizeUrl = String.format("%s/%s?ip=%s", authorizationBaseUrl, token, ip);

			AuthorizationResponseVO authorizationResp = restTemplate.getForObject(authorizeUrl,
					AuthorizationResponseVO.class, uriVars);
			Assert.assertThat(authorizationResp, Matchers.notNullValue());
			LOG.debug("authorizationResp:" + authorizationResp);
		} catch (Exception e) {
			LOG.error("fail", e);
			Assert.fail(e.getMessage());
		}
	}

	@Ignore
	@Test
	public void testAuthorizeInBatch() {
		String username = "283";
		String password = "123";

		String url = authenticationBaseUrl;
		AuthenticationRequestVO vo = new AuthenticationRequestVO(username, password);

		try {
			for (int i = 0; i < maxSizeToExecute; i++) {
				AuthenticationResponseVO authenticationResp = restTemplate.postForObject(url, prepareHttpEntity(vo),
						AuthenticationResponseVO.class, uriVars);
				Assert.assertThat(authenticationResp, Matchers.notNullValue());
				Assert.assertThat(authenticationResp.getToken(), Matchers.notNullValue());

				LOG.debug("token returned:" + authenticationResp.getToken());

				String token = authenticationResp.getToken();
				String ip = "127.0.0.1";
				String authorizeUrl = String.format("%s/%s?ip=%s", authorizationBaseUrl, token, ip);

				AuthorizationResponseVO authorizationResp = restTemplate.getForObject(authorizeUrl,
						AuthorizationResponseVO.class, uriVars);
				Assert.assertThat(authorizationResp, Matchers.notNullValue());
				LOG.debug("authorizationResp:" + authorizationResp);

			}
		} catch (Exception e) {
			LOG.error("fail", e);
			Assert.fail(e.getMessage());
		}
	}

	private HttpEntity<String> prepareHttpEntity(Object requestParams) {
		JSONObject jsonObj = JSONObject.fromObject(requestParams);
		HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), prepareHeaders());

		return formEntity;
	}

	private HttpHeaders prepareHeaders() {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		return headers;
	}

}
