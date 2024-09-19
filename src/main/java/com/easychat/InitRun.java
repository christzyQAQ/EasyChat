package com.easychat;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 20, 20243:41:37 PM
* @ClassName:InitRun.java

*/

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;

import com.easychat.redis.RedisUtils;
import com.easychat.websocket.netty.NettyWebSocketStarter;


@Component("initRun")
//应用启动后执行的一些初始化操作
public class InitRun implements ApplicationRunner {

	
	private static final Logger logger=LoggerFactory.getLogger(InitRun.class);
	
	@Resource
	private DataSource datasource;
	@Resource
	private RedisUtils redisUtils;
	@Resource
	private NettyWebSocketStarter webSocketStarter;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		try {
			datasource.getConnection();
			redisUtils.get("test");
			new Thread(webSocketStarter).start();
			logger.info("服务启动成功，可以开始开发了");
		} catch (SQLException e) {
			logger.error("数据库配置错误，请检查数据库配置");
		}catch (RedisConnectionFailureException e) {
			logger.error("redis服务配置异常，请检查redis配置",e);
		}catch (Exception e) {
			logger.error("服务启动失败");
		}
	}

}
