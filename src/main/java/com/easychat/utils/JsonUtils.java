package com.easychat.utils;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 29, 202410:44:42 AM
* @ClassName:JsonUtils.java

*/

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.exception.BusinessException;

public class JsonUtils {

	private static final Logger logger =LoggerFactory.getLogger(JsonUtils.class);
	
	public static SerializerFeature[] FRATURES =new SerializerFeature[] {SerializerFeature.WriteMapNullValue};
	
	public static String  convertObj2Json(Object object ) {return JSON.toJSONString(object, FRATURES);}
	
	public static <T> T convertJson2Obj(String json,Class<T> classz) {
		try {
			return JSONObject.parseObject(json, classz);
		} catch (Exception e) {
			logger.error("convertJson2Obj异常, json:{}",json);
			throw new BusinessException(ResponseCodeEnum.CODE_601);
		}
	}
	
	public static <T> List<T> convertJsonArray2List(String json, Class<T> classz) {
		try {
			return JSONObject.parseArray(json, classz);
		} catch (Exception e) {
			logger.error("convertJsonArray2List, json:{}",json,e);
			throw new BusinessException(ResponseCodeEnum.CODE_601);			
		}
	}
	
	
}
