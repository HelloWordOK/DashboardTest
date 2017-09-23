package com.nokia.charts.util;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	public static final Logger logger = Logger.getLogger(JsonUtils.class);

	public static ObjectMapper getJson() {
		return new ObjectMapper();
	}

	public static String object2jackson(Object obj) {
		String json = "";
		try {
			json = getJson().writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			logger.error(e);
		} catch (JsonMappingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return json;
	}

	public static <T> T json2object(String obj, Class<T> valueType) {
		T t = null;
		try {
			t = getJson().readValue(obj, valueType);
		} catch (JsonParseException e) {
			logger.error(e);
		} catch (JsonMappingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return t;
	}

	public static void main(String[] args) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("ss", "55");
		map.put("ss1", "55");
		map.put("ss2", "55");
		map.put("ss3", "55");
		map.put("ss4", "55");
		String s = JsonUtils.object2jackson(map);
		System.out.println(s);
	}
}