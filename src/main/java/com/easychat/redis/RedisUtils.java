package com.easychat.redis;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:2024年7月15日下午10:46:38
* @ClassName:RedisUtils.java

*/
@Component
public class RedisUtils<V> {
	@Resource
	private RedisTemplate<String, V> redisTemplate;
	private static final Logger logger=LoggerFactory.getLogger(RedisUtils.class);	 
		/*
		 * 删除缓存
		 * */
	public void delete(String... key) {
		List<String> keyList = Arrays.asList(key); // 将数组转换为 List<String>
        redisTemplate.delete(keyList); // 传递 List<String> 给 delete 方法    
    }
	
	 /*
     * 获取缓存
     */
	public V get(String key) {
		
		return key==null?null: redisTemplate.opsForValue().get(key);
    }
	
	/*
	 * 普通缓存放入
	 * 
	 * */
	public boolean set(String key, V value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			logger.error("设置redisKey:{} , value:{} 失败",key,value);
			return false;			
		}        
    }
	/*
	 * 普通缓存放入并设置时间
	 * 
	 * 
	 * */
	public boolean setex(String key, V value, long time) {
		try {
			if(time>0) {
				
				 redisTemplate.opsForValue().set(key, value, time,TimeUnit.SECONDS);
			}
			else {
				set(key, value);
			}
			return true;	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("设置RedisKey:{}, value:{} 失败",key, value);
			return false;
		}
       
    }
	
    /*
     * 设置指定 key 的过期时间
     *
     * @param key Redis 中的键名
     * @param time 过期时间（秒）
     * @return 设置是否成功
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, java.util.concurrent.TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	  /*
     * 向指定 key 对应的 Redis List 头部插入单个元素，并可设置过期时间
     *
     * @param key Redis 中的键名
     * @param value 要插入的值
     * @param time 过期时间（秒），如果不设置过期时间传入 <= 0 的值
     * @return 插入是否成功
     */
    public boolean lpush(String key, V value, long time) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (time > 0) {
                expire(key, time); // 设置过期时间
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * 从指定 key 对应的 Redis List 中移除指定值的元素
     *
     * @param key Redis 中的键名
     * @param value 要移除的值
     * @return 成功移除的元素数量
     */
    public long remove(String key, Object value) {
        try {
            long remove = redisTemplate.opsForList().remove(key, 1, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*
     * 向指定 key 对应的 Redis List 头部插入多个元素，并可设置过期时间
     *
     * @param key Redis 中的键名
     * @param values 要插入的值列表
     * @param time 过期时间（秒），如果不设置过期时间传入 <= 0 的值
     * @return 插入是否成功
     */
    public boolean lpushAll(String key, List<V > values, long time) {
        try {
            redisTemplate.opsForList().leftPushAll(key, values);
            if (time > 0) {
                expire(key, time); // 设置过期时间
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public void logRedisTemplateConfiguration() {
        logger.info("RedisTemplate keySerializer: {}", redisTemplate.getKeySerializer());
        logger.info("RedisTemplate valueSerializer: {}", redisTemplate.getValueSerializer());
    }
	 @PostConstruct
	    public void init() {
	        if (redisTemplate == null) {
	            logger.error("RedisTemplate bean is null!");
	        } else {
	            logger.info("RedisTemplate bean has been successfully injected!");
	        }
	    }

	public List<String> getQueueList(String key) {
		try {
	        // 从 Redis 中获取列表元素
	        List<V> range = redisTemplate.opsForList().range(key, 0, -1);
	        return range != null ? (List<String>) range : Collections.emptyList(); // 返回空列表而不是 null
	    } catch (Exception e) {
	        e.printStackTrace();
	        logger.error("从 Redis 中获取键名为 {} 的列表失败", key);
	        return Collections.emptyList(); // 返回空列表而不是 null
	    }
		
	}
	}






