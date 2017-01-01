package com.lachesis.support.auth.context.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lachesis.support.objects.vo.auth.AuthenticationRequestVO;
import com.lachesis.support.objects.vo.auth.AuthenticationResponseVO;

@Ignore
public class HttpClientTest {
	static final Logger LOG = LoggerFactory.getLogger(HttpClientTest.class);

	String authcHttpReqUrlTemplate = "http://127.0.0.1:9090/authc/api/v1/tokens";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPostAuthenticate() {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(authcHttpReqUrlTemplate);
		
		RequestConfig requestConfig = RequestConfig.custom()
		        .setSocketTimeout(5000)
		        .setConnectTimeout(5000)
		        .build();
		
		httpPost.setConfig(requestConfig);

		AuthenticationRequestVO requestVO = new AuthenticationRequestVO("283", "123");
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(requestVO);
		} catch (JsonProcessingException e) {
			throw new RuntimeException();
		}
		
		LOG.debug("JSON:"+json);
		
		
		httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
		httpPost.addHeader("Accept", "application/json;charset=utf-8");
		CloseableHttpResponse httpResp =  null;
		try {
			httpResp = httpClient.execute(httpPost);
			LOG.debug("status line:"+httpResp.getStatusLine());
			HttpEntity entity = httpResp.getEntity();
			LOG.debug(entity.getContentType().toString());
			
			String respString = EntityUtils.toString(entity, "UTF-8");
			
			LOG.debug("RESP:"+respString);
			
			AuthenticationResponseVO authcRespVO = mapper.readValue(respString, AuthenticationResponseVO.class);
			LOG.debug("resp VO:"+authcRespVO.toString());
		} catch (Exception e) {
			LOG.error("error on http", e);
			Assert.fail("http errors");
		}finally{
			if(httpResp != null){
				try {
					httpResp.close();
				} catch (IOException e) {
				}
			}
			
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}
	}

	@Test
	public void testGetAuthorize(){
		
	}
}
