package com.lachesis.support.auth;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lachesis.support.auth.spring.config.TestConfig;
import com.lachesis.support.objects.vo.auth.AuthenticationRequestVO;
import com.lachesis.support.objects.vo.auth.AuthenticationResponseVO;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class AuthenticationAndAuthorizationTest {
	static final Logger LOG = LoggerFactory.getLogger(AuthenticationAndAuthorizationTest.class);
	String authcHost = "192.168.0.107";
	int authcPort = 9090;
	String authcVersion = "v1";
	String bizHost = "192.168.0.105";
	int bizPort = 9091;
	String bizVersion = "v1";

	static final int TEN_THOUSAND = 1000 * 10;

	static final int MAX_ROUND_IN_BATCH_TEST = TEN_THOUSAND * 5;

	String authcBaseUrl = String.format("http://%s:%d/authc/api/%s/tokens", authcHost,authcPort,authcVersion);
	String appServerBaseUrl = String.format("http://%s:%d/demo/api/%s/nurses", bizHost,bizPort,bizVersion);

	RestTemplate restTemplate;
	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() throws Exception {
		restTemplate = new RestTemplate();
		prepareUserData();
	}
	
	private void prepareUserData(){
		
	}
	
	private <T> T get(String url,Class<T> cls){
		return null;
	}
	
	private <T> T post(String url,Object requestBody,Class<T> cls){
		return null;
	}

	@Test
	public void testAuthcAndAuthzWithSufficientPermissions() throws Exception {
		String username = "283";
		String password = "123";

		LOG.debug("STEP 1: try to login...");
		LOG.debug(String.format("Login with %s", username));

		pause();

		AuthenticationResponseVO respVO = authenticate(username, password);
		LOG.debug(String.format("Token:%s", respVO.getToken()));

		pause();
		LOG.debug("STEP 2:try to submit business request with token headers...");
		LOG.debug(String.format("Authorization:token %s", respVO.getToken()));
		pause();

		ResponseEntity<String> result = listNurses(respVO);
		LOG.debug(result.getBody());

		LOG.debug("STEP 3:try to logout...");
		logout(respVO.getToken());

	}

	@Test
	public void testAuthcAndAuthzWithSufficientPermissionsInBatch() throws Exception {
		String username = "283";
		String password = "123";

		String batchTests = System.getProperty("batchTests");

		int maxRound = 0;
		if (batchTests != null) {
			if (batchTests.trim().length() > 0) {
				try {
					maxRound = Integer.parseInt(batchTests.trim());
				} catch (Exception e) {
					maxRound = MAX_ROUND_IN_BATCH_TEST;
				}
			} else {
				maxRound = MAX_ROUND_IN_BATCH_TEST;
			}
		}

		long st = System.currentTimeMillis();
		for (int i = 0; i < maxRound; i++) {
			LOG.debug("STEP 1: try to login..." + i);
			LOG.debug(String.format("Login with %s", username));

			AuthenticationResponseVO respVO = authenticate(username, password);
			LOG.debug(String.format("Token:%s", respVO.getToken()));

			LOG.debug("STEP 2:try to submit business request with token headers...");
			LOG.debug(String.format("Authorization:token %s", respVO.getToken()));

			ResponseEntity<String> result = listNurses(respVO);
			LOG.debug(result.getBody());

			LOG.debug("STEP 3:try to logout...");
			logout(respVO.getToken());
		}

		long cur = System.currentTimeMillis();
		long elapseMinutes = ((cur - st) / (1000 * 60));
		LOG.info(String.format("%d cases executed and time elapse %d minutes", maxRound, elapseMinutes));

	}

	private void logout(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		HttpEntity<String> reqEntity = new HttpEntity<String>(headers);
		String url = authcBaseUrl + "/" + token;
		ResponseEntity<String> respEntity = restTemplate.exchange(url, HttpMethod.DELETE, reqEntity, String.class);
		Assert.assertThat(respEntity, Matchers.notNullValue());
		Assert.assertThat(respEntity.getStatusCode().is2xxSuccessful(), Matchers.is(true));
	}

	private ResponseEntity<String> listNurses(AuthenticationResponseVO respVO) {
		HttpEntity<String> requestEntity = prepareRequestEntityForListNurses(respVO);
		String url = appServerBaseUrl + "?deptid=1";
		ResponseEntity<String> respEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		Assert.assertThat(respEntity, Matchers.notNullValue());
		return respEntity;
	}

	private HttpEntity<String> prepareRequestEntityForListNurses(AuthenticationResponseVO respVO) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		headers.add("Authorization", String.format("token %s", respVO.getToken()));

		HttpEntity<String> reqEntity = new HttpEntity<String>(headers);
		return reqEntity;
	}

	private void pause() throws IOException {
		// System.in.read();
	}

	private AuthenticationResponseVO authenticate(String username, String password)
			throws RestClientException, JsonProcessingException {
		AuthenticationRequestVO requestVO = new AuthenticationRequestVO(username, password);
		AuthenticationResponseVO respVO = restTemplate.postForObject(authcBaseUrl, prepareHttpEntity(requestVO),
				AuthenticationResponseVO.class);
		Assert.assertThat(respVO, Matchers.notNullValue());
		Assert.assertThat(respVO.getToken(), Matchers.notNullValue());

		return respVO;
	}

	private HttpEntity<String> prepareHttpEntity(Object requestParams) throws JsonProcessingException {
		String sJson = mapper.writeValueAsString(requestParams);
		HttpEntity<String> formEntity = new HttpEntity<String>(sJson, prepareHeaders());

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
