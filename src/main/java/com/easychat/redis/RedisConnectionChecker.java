package com.easychat.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:2024年7月16日下午2:59:03
* @ClassName:RedisConnectionChecker.java

*/
@Component

public class RedisConnectionChecker<V>{
	   private static final Logger logger = LoggerFactory.getLogger(RedisConnectionChecker.class);
	@Autowired 
	   private  RedisTemplate<String, V> redisTemplate;

	    public void checkRedisConnection() {
	        try {
	            redisTemplate.opsForValue().set("testKey",  (V) "testValue");
	            logger.info("成功连接到 Redis 服务器");
	        } catch (Exception e) {
	            logger.error("连接到 Redis 服务器失败", e);
	        }
	    }
}
