package com.easychat.redis;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:2024年7月15日下午5:35:15
* @ClassName:RedisConfig.java

*/

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig<V> {
	private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
	@Value("${spring.redis.host:}")
	private String redisHost;

	@Value("${spring.redis.port:}")
	private Integer redisPort;

	@Bean(name = "redissonClient", destroyMethod = "shutdown")	
	public RedissonClient redissonClient() {
		try {
			Config config = new Config();
			config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);		
			RedissonClient redissonClient = Redisson.create(config);
			return redissonClient;
		} catch (Exception e) {
			logger.error("redis配置错误，请检查redis配置");
		}
		return null;
	}

	@Bean("redisTemplate")
	public RedisTemplate<String, V> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, V> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		// 设置 key 的序列化方式
		template.setKeySerializer(RedisSerializer.string());
		// 设置 value 的序列化方式
		template.setValueSerializer(RedisSerializer.json());
		// 设置 hash key 的序列化方式
		template.setHashKeySerializer(RedisSerializer.string());
		// 设置 hash value 的序列化方式
		template.setHashValueSerializer(RedisSerializer.json());
		template.afterPropertiesSet();
		System.out.println("RedisTemplate bean has been successfully created!");
		return template;
	}

}