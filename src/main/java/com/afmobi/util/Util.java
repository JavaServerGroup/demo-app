package com.afmobi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
	
	private static ObjectMapper jacksonMapper = new ObjectMapper();
	
	public static String BeanToJson(Object bean){
		String json = "";
		try {
			json =  jacksonMapper.writeValueAsString(bean);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
}
