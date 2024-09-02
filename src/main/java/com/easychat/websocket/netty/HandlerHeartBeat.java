package com.easychat.websocket.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 27, 20245:39:12 PM
* @ClassName:HandlerHeartBeat.java

*/

@Component
public class HandlerHeartBeat extends ChannelDuplexHandler{

	private static final Logger logger=LoggerFactory.getLogger(HandlerHeartBeat.class);
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e =(IdleStateEvent)evt;
			
			if (e.state()==IdleState.READER_IDLE) {
				Channel channel =ctx.channel();
				Attribute<String> attribute= channel.attr(AttributeKey.valueOf(channel.id().toString()));
				String userId=attribute.get();
				logger.info("用户{}心跳超时",userId);
				ctx.close();
				
			}else  if (e.state()==IdleState.READER_IDLE) {
				ctx.writeAndFlush("heart");
			}
		}
	}	
}
