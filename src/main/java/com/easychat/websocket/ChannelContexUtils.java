package com.easychat.websocket;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 28, 202411:45:21 AM
* @ClassName:ChannelContexUtils.java

*/

import java.applet.Applet;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.easychat.dto.MessageSendDto;
import com.easychat.dto.WsInitDate;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.po.ContactApply;
import com.easychat.entity.po.Info;
import com.easychat.entity.po.Message;
import com.easychat.entity.po.SessionUser;
import com.easychat.entity.query.ContactApplyQuery;
import com.easychat.entity.query.InfoQuery;
import com.easychat.entity.query.MessageQuery;
import com.easychat.entity.query.SessionUserQuery;
import com.easychat.enums.MessageTypeEnum;
import com.easychat.enums.UserContactApplyStatusEnum;
import com.easychat.enums.UserContactStatusEnum;
import com.easychat.enums.UserContactTypeEnum;
import com.easychat.mappers.ContactApplyMapper;
import com.easychat.mappers.InfoMapper;
import com.easychat.mappers.MessageMapper;
import com.easychat.mappers.SessionUserMapper;
import com.easychat.redis.RedisComponent;
import com.easychat.service.ContactApplyService;
import com.easychat.service.SessionUserService;
import com.easychat.utils.JsonUtils;
import com.easychat.utils.StringTools;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

@Component
public class ChannelContexUtils {

	private static final Logger logger = LoggerFactory.getLogger(ChannelContexUtils.class);

	private static final ConcurrentHashMap<String, Channel> USER_CONTEXT_MAP = new ConcurrentHashMap<>();

	private static final ConcurrentHashMap<String, ChannelGroup> GROUP_CONTEXT_MAP = new ConcurrentHashMap<>();

	@Resource
	private InfoMapper<Info, InfoQuery> infoMapper;

	@Resource
	private SessionUserMapper<SessionUser,SessionUserQuery> sessionUserMapper;

	@Resource
	private RedisComponent redisComponent;

	@Resource
	private ContactApplyMapper<ContactApply, ContactApplyQuery> contactApplyMapper;

	@Resource
	private MessageMapper<Message, MessageQuery> messageMapper;

	public void addContext(String userId, Channel channel) {
		String channelId = channel.id().toString();
		logger.info("channelId:{}", channelId);
		AttributeKey attributeKey = null;
		if (!AttributeKey.exists(channelId)) {
			attributeKey = AttributeKey.newInstance(channelId);
		} else {
			attributeKey = AttributeKey.valueOf(channelId);
		}
		channel.attr(attributeKey).set(userId);
		List<String> contactIdList = redisComponent.getUserContactList(userId);
		for (String groupId : contactIdList) {
			if (groupId.startsWith(UserContactTypeEnum.GROUP.getPrefix())) {
				add2Group(groupId, channel);
			}
		}

		USER_CONTEXT_MAP.put(userId, channel);
		redisComponent.saveHeartBeat(userId);

		// 更新用户最后连接时间
		Info updateInfo = new Info();
		updateInfo.setLastLoginTime(new Date());
		infoMapper.updateByUserId(updateInfo, userId);

		// 给用户发送消息（三天离线消息）
		Info info = infoMapper.selectByUserId(userId);
		Date sourceLastOffTime = info.getLastOffTime();
		Date curDate = new Date();
		Long lastOffTime = sourceLastOffTime.getTime();
		if (sourceLastOffTime != null
				&& curDate.getTime() - Constants.MILLISECONDS_3DAYS_AGO > sourceLastOffTime.getTime()) {
			lastOffTime = Constants.MILLISECONDS_3DAYS_AGO;
		}
		// 1、查询会话信息 查询用户所有会话信息，即换设备也能保证会话信息列表与原设备一致
		SessionUserQuery sessionUserQuery = new SessionUserQuery();
		sessionUserQuery.setUserId(userId);
		sessionUserQuery.setOrderBy("last_recieve_time desc");
		List<SessionUser> chatSessionlList = this.sessionUserMapper.selectList(sessionUserQuery);
		WsInitDate wsInitDate = new WsInitDate();
		wsInitDate.setSessionUserList(chatSessionlList);

		// 2、查询聊天消息
		List<String> groupIdList = contactIdList.stream()
				.filter(item -> item.startsWith(UserContactTypeEnum.GROUP.GROUP.getPrefix()))
				.collect(Collectors.toList());
		groupIdList.add(userId);
		MessageQuery messageQuery = new MessageQuery();
		messageQuery.setContactIdList(contactIdList);
		messageQuery.setLastReciveTime(lastOffTime);
		List<Message> messageList = messageMapper.selectList(messageQuery);
		wsInitDate.setMessagesList(messageList);

		// 3、查询用户好友申请
		ContactApplyQuery contactApplyQuery = new ContactApplyQuery();
		contactApplyQuery.setLastApplyTimestamp(lastOffTime);
		contactApplyQuery.setReceiveUserId(userId);
		contactApplyQuery.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
		Integer applyCount = contactApplyMapper.selectCount(contactApplyQuery);
		wsInitDate.setApplyCount(applyCount);

		// 发送消息
		MessageSendDto messageSendDto = new MessageSendDto();
		messageSendDto.setMessageType(MessageTypeEnum.INIT.getType());
		messageSendDto.setContactId(channelId);
		messageSendDto.setExtendData(wsInitDate);

	}


//	private void add2Group(String groupId, Channel channel) {
//
//		ChannelGroup group = GROUP_CONTEXT_MAP.get(groupId);
//		if (group == null) {
//			group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//			GROUP_CONTEXT_MAP.put(groupId, group);
//
//		}
//		if (channel == null) {
//			return;
//		}
//		group.add(channel);
//	}


	public String getuserIdbyChannel(Channel channel) {
		Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
		String userId = attribute.get();
		return userId;
	}

	public void removeContext(Channel channel) {
		String userId = getuserIdbyChannel(channel);
		if (StringTools.isEmpty(userId)) {
			USER_CONTEXT_MAP.remove(userId);
		}
		redisComponent.removeHeartBeats(userId);
		// 更新用户最后离线时间
		Info info = new Info();
		info.setLastOffTime(new Date());
		infoMapper.updateByUserId(info, userId);

	}

	public void sendMessage(MessageSendDto messageSendDto) {
		UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getByprefix(messageSendDto.getContactId());
		switch (contactTypeEnum) {
		case USER:
			send2User(messageSendDto);
			break;
		case GROUP:

			break;

		default:
			break;
		}

	}

	// 发送消息给用户
	public void send2User(MessageSendDto messageSendDto) {
		String contactId = messageSendDto.getContactId();
		if (StringTools.isEmpty(contactId)) {
			return;
		}
		sendMsg(messageSendDto, contactId);
		
		// 强制下线功能
		if (MessageTypeEnum.FORCE_OFF_LINE.getType().equals(messageSendDto.getMessageType())) {
			closeContext(contactId);
		}

	}

	// 发送消息给群组
	public void send2Group(MessageSendDto messageSendDto) {
		if (StringTools.isEmpty(messageSendDto.getContactId())) {
			return;
		}
		ChannelGroup channelGroup =GROUP_CONTEXT_MAP.get(messageSendDto.getContactId());
		if (channelGroup==null) {
			return;
		}
		channelGroup.writeAndFlush(new TextWebSocketFrame(JsonUtils.convertObj2Json(messageSendDto)));
		
		
		
	}

	// 发送消息
	public void sendMsg(MessageSendDto messageSendDto, String recievId) {
		Channel userChannel = USER_CONTEXT_MAP.get(recievId);
		if (userChannel == null) {
			return;
		}
		// 对于客户端而言，联系人就是发送人，因此转换一下再发送,好友申请时不处理
		if (MessageTypeEnum.ADD_FRIEDN_SELF.getType().equals(messageSendDto.getMessageType())) {
			Info info =(Info)messageSendDto.getExtendData();
			messageSendDto.setMessageType(MessageTypeEnum.ADD_FRIEDN.getType());
			messageSendDto.setContactId(info.getUserId());
			messageSendDto.setContactName(info.getNickName());
			messageSendDto.setExtendData(null);
			
				
		}
		else {
			messageSendDto.setContactId(messageSendDto.getSendUserId());
			messageSendDto.setContactName(messageSendDto.getSendUserNickName());
		}
		
		userChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.convertObj2Json(messageSendDto)));

	}

	public void closeContext(String userId) {
		if (StringTools.isEmpty(userId)) {
			return;
		}
		redisComponent.cleanUserTokenByUserId(userId);
		Channel channel = USER_CONTEXT_MAP.get(userId);
		if (channel == null) {
			return;
		}
		channel.close();
	}
	// 将用户加入到groupChannel，实现群聊	
	public void addUser2Group(String userId, String groupId) {
	    Channel channel = USER_CONTEXT_MAP.get(userId);
	    add2Group(groupId, channel);
	}

	private void add2Group(String groupId, Channel context) {
	    ChannelGroup group = GROUP_CONTEXT_MAP.get(groupId);
	    if (group == null) {
	        group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	        GROUP_CONTEXT_MAP.put(groupId, group);
	    }
	    if (context == null) {
	        return;
	    }
	    group.add(context);
	}


}
