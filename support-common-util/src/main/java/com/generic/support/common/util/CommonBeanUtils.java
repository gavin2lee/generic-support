package com.generic.support.common.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class CommonBeanUtils {
	private static final Logger LOG = LoggerFactory.getLogger(CommonBeanUtils.class);
	private CommonBeanUtils(){}
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String toJson(Object obj){
		try {
			String json = mapper.writeValueAsString(obj);
			return json;
		} catch (JsonProcessingException e) {
			LOG.warn(e.getMessage(), e);
			return null;
		}
		
	}
	
	public static <T> T toObject(String sJson, Class<T> valueType){
		T result = null;
		try {
			result = mapper.readValue(sJson, valueType);
			return result;
		} catch (IOException e) {
			LOG.warn(e.getMessage(), e);
			return null;
		}
	}
}
