package com.easychat.websocket;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.easychat.dto.MessageSendDto;
import com.easychat.utils.JsonUtils;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 29, 202411:00:53 PM
* @ClassName:MessageHandler.java 消息处理器

*/
@Component
public class MessageHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
	
	private static final String MESSAGE_TOPIC="message.topic";
	
	@Resource
	private RedissonClient redissonClient;

	@Resource
	private ChannelContextUtils channelContextUtils;
	
	@PostConstruct
	public void lisMessage() {
		RTopic rTopic =redissonClient.getTopic(MESSAGE_TOPIC);
		rTopic.addListener(MessageSendDto.class, (MessageSendDto, sendDto)->{
		logger.info("收到广播消息:{}",JsonUtils.convertObj2Json(sendDto));
		channelContextUtils.sendMessage(sendDto);
		});
	}

	/**
	 * 发布消息
	 * @param messageSendDto
	 */
	public void sendMessage(MessageSendDto messageSendDto) {
		RTopic rTopic =redissonClient.getTopic(MESSAGE_TOPIC);
		rTopic.publish(messageSendDto);
	}
	

}
