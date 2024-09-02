package com.easychat.websocket.netty;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.easychat.dto.TokenUserInfoDto;
import com.easychat.redis.RedisComponent;
import com.easychat.utils.StringTools;
import com.easychat.websocket.ChannelContexUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 27, 20248:19:41 PM
* @ClassName:HanderWebSocket.java

*/
@Component
@ChannelHandler.Sharable
public class HandlerWebSocket extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	
	private static final Logger logger=LoggerFactory.getLogger(HandlerWebSocket.class);
	
	@Resource
	private RedisComponent redisComponent;
	
	@Resource
	private ChannelContexUtils channelContexUtils;


	/**
	 *通道就绪后调用，一般用来做初始化
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("有新的连接加入...");	
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("有连接断开...");
		channelContexUtils.removeContext(ctx.channel());
	}



	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
		Channel channel =ctx.channel();		
		String userId=channelContexUtils.getuserIdbyChannel(channel);
		logger.info("收到userId{}的消息:{}",userId,textWebSocketFrame.text());
		redisComponent.saveHeartBeat(userId);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
			WebSocketServerProtocolHandler.HandshakeComplete complete
			=(WebSocketServerProtocolHandler.HandshakeComplete) evt;
			String url =complete.requestUri();
			String token =getToken(url);
			if(token==null) {
				ctx.channel().close();
				return;
			}	
			TokenUserInfoDto tokenUserInfoDto =redisComponent.getTokenUserInfoDto(token);
			if(tokenUserInfoDto==null||tokenUserInfoDto.getToken()==null) {
				ctx.channel().close();
				return;
			}
			channelContexUtils.addContext(tokenUserInfoDto.getUserId(), ctx.channel());			
					
		}
		
	}

	private String getToken(String url)throws Exception {
		
		
		if (StringTools.isEmpty(url)||url.indexOf("%")==-1)
		{
			return null;
		}
		String[] queryParams =url.split("\\%");
		if (queryParams.length!=2) {
			return null;		
		}
		String[] param = queryParams[1].split("=");
		if (param.length!=2) {
			return null;
		}
		return param[1];			
	}
				
}


