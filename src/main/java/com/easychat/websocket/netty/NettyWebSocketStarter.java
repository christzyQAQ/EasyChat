package com.easychat.websocket.netty;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.easychat.entity.config.AppConfig;
import com.easychat.utils.StringTools;

import io.lettuce.core.event.Event;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 27, 20245:08:34 PM
* @ClassName:NettyWebSocket.java

*/

@Component
public class NettyWebSocketStarter implements Runnable{
	
	private static final Logger logger=LoggerFactory.getLogger(NettyWebSocketStarter.class);

	private static  EventLoopGroup bossGroup=new NioEventLoopGroup() ;
	
	private static EventLoopGroup workGroup =new NioEventLoopGroup();
	
	@Resource
	private HandlerWebSocket handlerWebSocket;
	
	@Resource
	private AppConfig appConfig;
	
	@PreDestroy
	private void close() {
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
	}
	


	@Override
	public void run() {
		
		try {
			ServerBootstrap serverBootstrap =new ServerBootstrap();
			serverBootstrap.group(bossGroup,workGroup);
			serverBootstrap.channel(NioServerSocketChannel.class).
			handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer() {

				@Override
				protected void initChannel(Channel channel) throws Exception {
					ChannelPipeline pipeline = channel.pipeline();
					//设置几个重要的处理器
					//对http协议的支持，使用http的编码器、解码器
					pipeline.addLast(new HttpServerCodec());
					//聚合解码 httpRequest/httpContent/lastHttpContent到fullHttpRequest
					//保证接受Http请求的完整性
					pipeline.addLast(new HttpObjectAggregator(64*1024));
					//心跳  long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit
					//readerIdleTime 读超时时间，即测试端一定时间内未接收到测试端的消息
					//writerIdleTime 写超时时间，即测试端一定时间内向被测试端发送消息
					//allIdleTime 所有类型的超时时间
					//unit 单位时间 设为秒
					pipeline.addLast(new IdleStateHandler(600, 0, 0, TimeUnit.SECONDS));
					pipeline.addLast(new HandlerHeartBeat());
					//将http协议升级为ws协议，对websocket支持
					pipeline.addLast(new WebSocketServerProtocolHandler("/ws",null,true,64*1024,true,true,10000L));
					pipeline.addLast(handlerWebSocket); // 添加消息处理器
					
					
				}
			} ); 
			Integer WsPort=appConfig.getWsport();
			String WsPortStr=System.getProperty("ws.port");
			if (!StringTools.isEmpty(WsPortStr)) {
				WsPort=Integer.parseInt(WsPortStr);
			}
			ChannelFuture channelFuture =serverBootstrap.bind(WsPort).sync();
			logger.info("netty启动成功,端口为："+appConfig.getWsport());
			channelFuture.channel().closeFuture().sync();
			
			
		} catch (Exception e) {
			logger.error("启动netty失败",e);
		}finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	
		
		
	
}
