package com.easychat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easychat.enums.GroupStatusEnum;
import com.easychat.enums.JoinTypeEnum;
import com.easychat.enums.MessageStatusEnum;
import com.easychat.enums.MessageTypeEnum;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.enums.UserContactApplyStatusEnum;
import com.easychat.enums.UserContactStatusEnum;
import com.easychat.enums.UserContactTypeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.dto.MessageSendDto;
import com.easychat.dto.SysSettingDto;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.po.Contact;
import com.easychat.entity.po.ContactApply;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.po.Info;
import com.easychat.entity.po.Message;
import com.easychat.entity.po.Session;
import com.easychat.entity.po.SessionUser;
import com.easychat.entity.query.ContactApplyQuery;
import com.easychat.entity.query.ContactQuery;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.entity.query.InfoQuery;
import com.easychat.entity.query.MessageQuery;
import com.easychat.entity.query.SessionQuery;
import com.easychat.entity.query.SessionUserQuery;
import com.easychat.entity.query.SimplePage;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.service.ContactApplyService;
import com.easychat.utils.CopyTools;
import com.easychat.utils.StringTools;
import com.easychat.websocket.ChannelContextUtils;
import com.easychat.websocket.MessageHandler;

import jodd.util.ArraysUtil;

import com.easychat.mappers.ContactApplyMapper;
import com.easychat.mappers.ContactMapper;
import com.easychat.mappers.GroupInfoMapper;
import com.easychat.mappers.InfoMapper;
import com.easychat.mappers.MessageMapper;
import com.easychat.mappers.SessionMapper;
import com.easychat.mappers.SessionUserMapper;
import com.easychat.redis.RedisComponent;

/**
 * ServiceImpl
 * 
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
@Service("contactApplyService")
public class ContactApplyServiceImpl implements ContactApplyService {

	@Resource
	private ContactApplyMapper<ContactApply, ContactApplyQuery> contactApplyMapper;

	@Resource
	private ContactMapper<Contact, ContactQuery> contactMapper;

	@Resource
	private GroupInfoMapper<GroupInfo, GroupInfoQuery> groupInfoMapper;

	@Resource
	private RedisComponent redisComponent;

	@Resource
	private SessionMapper<Session, SessionQuery> sessionMapper;

	@Resource
	private SessionUserMapper<SessionUser, SessionUserQuery> sessionUserMapper;

	@Resource
	private MessageMapper<Message, MessageQuery> messageMapper;

	@Resource
	private MessageHandler messageHandler;
	
	@Resource
	private ChannelContextUtils channelContexUtils;
	

	@Resource
	private InfoMapper<Info, InfoQuery> infoMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<ContactApply> findListByParam(ContactApplyQuery query) {
		return this.contactApplyMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(ContactApplyQuery query) {
		return this.contactApplyMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<ContactApply> findListByPage(ContactApplyQuery query) {
		Integer count = this.findCountByParam(query);
		SimplePage page = new SimplePage(query.getPageNo(), query.getPageSize(), count);
		query.setSimplePage(page);
		List<ContactApply> list = this.findListByParam(query);
		PaginationResultVO<ContactApply> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(),
				page.getCountPage(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(ContactApply bean) {
		return this.contactApplyMapper.insert(bean);
	}

	/**
	 * 新增批量
	 */
	public Integer addBatch(List<ContactApply> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.contactApplyMapper.insertBatch(listBean);
	}

	/**
	 * 新增批量或修改
	 */
	public Integer addOrUpdateBatch(List<ContactApply> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.contactApplyMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据ApplyId查询
	 */
	public ContactApply selectByApplyId(Integer applyId) {
		return this.contactApplyMapper.selectByApplyId(applyId);
	}

	/**
	 * 根据ApplyId更新
	 */
	public Integer updateByApplyId(ContactApply bean, Integer applyId) {
		return this.contactApplyMapper.updateByApplyId(bean, applyId);
	}

	/**
	 * 根据ApplyId删除
	 */
	public Integer deleteByApplyId(Integer applyId) {
		return this.contactApplyMapper.deleteByApplyId(applyId);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndContactId查询
	 */
	public ContactApply selectByApplyUserIdAndReceiveUserIdAndContactId(String applyUserId, String receiveUserId,
			String contactId) {
		return this.contactApplyMapper.selectByApplyUserIdAndReceiveUserIdAndContactId(applyUserId, receiveUserId,
				contactId);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndContactId更新
	 */
	public Integer updateByApplyUserIdAndReceiveUserIdAndContactId(ContactApply bean, String applyUserId,
			String receiveUserId, String contactId) {
		return this.contactApplyMapper.updateByApplyUserIdAndReceiveUserIdAndContactId(bean, applyUserId, receiveUserId,
				contactId);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndContactId删除
	 */
	public Integer deleteByApplyUserIdAndReceiveUserIdAndContactId(String applyUserId, String receiveUserId,
			String contactId) {
		return this.contactApplyMapper.deleteByApplyUserIdAndReceiveUserIdAndContactId(applyUserId, receiveUserId,
				contactId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer applyAdd(TokenUserInfoDto tokenUserInfoDto, String contactId, String applyInfo) {

		UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getByprefix(contactId);
		if (contactTypeEnum == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		// 申请人
		String applyUserId = tokenUserInfoDto.getUserId();
		// 默认申请信息
		applyInfo = StringTools.isEmpty(applyInfo)
				? String.format(Constants.APPLY_INFO_DEFAULT, tokenUserInfoDto.getNickName())
				: applyInfo;
		Date curDate = new Date();
		Integer joinType = null;
		String recieveUserid = contactId;

		// 查询对方是否已经添加或如果被拉黑则无法再次添加
		Contact contact = contactMapper.selectByUserIdAndContactId(applyUserId, contactId);

		if (contact != null && ArraysUtil.contains(new Integer[] { UserContactStatusEnum.FRIEND.getStatus(),
				UserContactStatusEnum.FIRST_BLACKLIST_BE.getStatus(), UserContactStatusEnum.BLACKLIST_BE.getStatus() },
				contact.getStatus())) {
			throw new BusinessException("对方已在您的联系人列表中或您已被拉黑，无法添加");
		}
		if (contactTypeEnum == UserContactTypeEnum.GROUP) {
			GroupInfo groupInfo = groupInfoMapper.selectByGroupId(contactId);
			if (groupInfo == null || GroupStatusEnum.DISSOLUTION.getStatus().equals(groupInfo.getStatus())) {
				throw new BusinessException("群聊不存在或该群已解散");
			}

			recieveUserid = groupInfo.getGroupOwnerId();
			joinType = groupInfo.getJoinType();
		} else {
			Info info = infoMapper.selectByUserId(contactId);
			if (info == null) {
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}
			joinType = info.getJoinType();
		}
		// 直接加入不需要申请
		if (JoinTypeEnum.JOIN.getType().equals(joinType)) {
			addContact(applyUserId, recieveUserid, contactId, contactTypeEnum.getType(), applyInfo);
			return joinType;
		}
		ContactApply dbApply = this.contactApplyMapper.selectByApplyUserIdAndReceiveUserIdAndContactId(applyUserId,
				recieveUserid, contactId);
		if (dbApply == null) {
			ContactApply contactApply = new ContactApply();
			contactApply.setApplyUserId(applyUserId);
			contactApply.setContactType(contactTypeEnum.getType());
			contactApply.setContactId(contactId);
			contactApply.setReceiveUserId(recieveUserid);
			contactApply.setLastApplyTime(curDate);
			contactApply.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
			contactApply.setApplyInfo(applyInfo);
			this.contactApplyMapper.insert(contactApply);
		} else {
			// 更新状态
			ContactApply contactApply = new ContactApply();
			contactApply.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
			contactApply.setApplyInfo(applyInfo);
			contactApply.setLastApplyTime(curDate);
			this.contactApplyMapper.updateByApplyId(contactApply, dbApply.getApplyId());
		}

		if (dbApply == null || !UserContactApplyStatusEnum.INIT.getStatus().equals(dbApply.getStatus())) {

			//发送ws消息
			MessageSendDto messageSendDto = new MessageSendDto();
			messageSendDto.setMessageType(MessageTypeEnum.ADD_FRIEDN.getType());
			messageSendDto.setMessageContent(applyInfo);
			messageSendDto.setContactId(recieveUserid);
			messageHandler.sendMessage(messageSendDto);

		}
		return joinType;

	}

	// 申请信息处理：根据三种状态值分别处理：1：接受，2拒绝，3拉黑
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void dealWithApply(String userId, Integer applyId, Integer status) {
		Date curDate = new Date();
		UserContactApplyStatusEnum statusEnum = UserContactApplyStatusEnum.getByStatus(status);
		// 如果发送状态为空或为初始状态
		if (statusEnum == null || UserContactApplyStatusEnum.INIT == statusEnum) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		ContactApply applyInfo = this.contactApplyMapper.selectByApplyId(applyId);
		// 判断申请信息是否为空或者处理人是否为申请接收者
		if (applyInfo == null || !userId.equals(applyInfo.getReceiveUserId())) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		// 更新用户信息，且只能由初始状态改变为其他状态
		ContactApply updateInfo = new ContactApply();
		updateInfo.setStatus(status);
		updateInfo.setLastApplyTime(curDate);

		ContactApplyQuery applyQuery = new ContactApplyQuery();
		applyQuery.setApplyId(applyId);
		applyQuery.setStatus(UserContactApplyStatusEnum.INIT.getStatus());

		Integer count = contactApplyMapper.updateByApplyId(updateInfo, applyId);
		if (count == 0) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		// 同意好友申请
		if (UserContactApplyStatusEnum.PASS.getStatus().equals(status)) {
			this.addContact(applyInfo.getApplyUserId(), applyInfo.getReceiveUserId(), applyInfo.getContactId(),
					applyInfo.getContactType(), applyInfo.getApplyInfo());

			return;
		}
		// 拒绝好友申请
		if (UserContactApplyStatusEnum.REJECT.getStatus().equals(status)) {
			return;
		}
		// 拉黑此申请
		if (UserContactApplyStatusEnum.BLACKLIST.getStatus().equals(status)) {
			Contact contact = new Contact();
			contact.setUserId(applyInfo.getApplyUserId());
			contact.setContactId(applyInfo.getContactId());
			contact.setContactType(applyInfo.getContactType());
			contact.setCreateTime(curDate);
			contact.setStatus(UserContactStatusEnum.FIRST_BLACKLIST_BE.getStatus());
			contact.setLastUpdateTime(curDate);
			contactMapper.insertOrUpdate(contact);
		}

	}

	/**
	 *
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addContact(String applyUserId, String receiveUserId, String contactId, Integer contactType,
			String applyInfo) {
		// 如果是加入群组，获取群聊人数
		if (UserContactTypeEnum.GROUP.getType().equals(contactType)) {
			ContactQuery contactQuery = new ContactQuery();
			contactQuery.setContactId(contactId);
			contactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
			Integer memberCount = contactMapper.selectCount(contactQuery);
			SysSettingDto settingDto = new SysSettingDto();
			if (memberCount >= settingDto.getMaxGroupMemberCount()) {
				throw new BusinessException("该群人数已达上限，无法加入");
			}
		}
		Date curDate = new Date();
		// 同意,双方添加好友
		List<Contact> contactList = new ArrayList<>();
		Contact contact = new Contact();
		// 申请人添加对方
		contact.setContactId(contactId);// 对方
		contact.setUserId(applyUserId);// 申请人
		contact.setContactType(contactType);
		contact.setCreateTime(curDate);
		contact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
		contact.setLastUpdateTime(curDate);
		contactList.add(contact);
		// 如果是申请好友，则添加申请人为好友，是群组则不用添加申请人为好友
		if (UserContactTypeEnum.USER.getType().equals(contactType)) {
			contact = new Contact();
			contact.setContactId(applyUserId);
			contact.setUserId(receiveUserId);
			contact.setContactType(contactType);
			contact.setCreateTime(curDate);
			contact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
			contact.setLastUpdateTime(curDate);
			contactList.add(contact);
		}
		// 批量插入
		contactMapper.insertOrUpdateBatch(contactList);

		if (UserContactTypeEnum.USER.getType().equals(contactType)) {
			// 申请接收人将申请人添加进自己的好友缓存
			redisComponent.addUserContact(receiveUserId, applyUserId);
		}
		// 申请人将群组或好友添加进自己的好友缓存
		redisComponent.addUserContact(applyUserId, contactId);
		// 创建会话
		String sessionId = null;
		if (UserContactTypeEnum.USER.getType().equals(contactType)) {
			sessionId = StringTools.getChatSessionId4User(new String[] { contactId, applyUserId });
		} else {
			sessionId = StringTools.getChatSessionId4Group(contactId);
		}
		List<SessionUser> sessionUserList = new ArrayList<>();
		if (UserContactTypeEnum.USER.getType().equals(contactType)) {
			// 创建会话
			Session session = new Session();
			session.setSessionId(sessionId);
			session.setLastMessage(applyInfo);
			session.setLastRecieveTime(curDate.getTime());
			this.sessionMapper.insertOrUpdate(session);
			// 申请人session
			SessionUser applySessionUser = new SessionUser();
			applySessionUser.setUserId(applyUserId);
			applySessionUser.setContactId(contactId);
			applySessionUser.setSessionId(sessionId);
			Info contactUserInfo = this.infoMapper.selectByUserId(contactId);
			applySessionUser.setContactName(contactUserInfo.getNickName());
			sessionUserList.add(applySessionUser);			

			// 接收人session
			SessionUser reciveSessionUser = new SessionUser();
			reciveSessionUser.setUserId(receiveUserId);
			reciveSessionUser.setContactId(applyUserId);
			reciveSessionUser.setSessionId(sessionId);
			Info applyUserInfo = this.infoMapper.selectByUserId(applyUserId);
			reciveSessionUser.setContactName(applyUserInfo.getNickName());
			sessionUserList.add(reciveSessionUser);
			this.sessionUserMapper.insertOrUpdateBatch(sessionUserList);

			// 记录消息表
			Message chatMessage = new Message();
			chatMessage.setSessionId(sessionId);
			chatMessage.setContactId(contactId);
			chatMessage.setSendUserId(applyUserId);
			chatMessage.setSendUserNickName(applyUserInfo.getNickName());
			chatMessage.setMessageContent(applyInfo);
			chatMessage.setContactType(UserContactTypeEnum.USER.getType());
			chatMessage.setSendTime(curDate.getTime());
			chatMessage.setMessageType(MessageTypeEnum.ADD_FRIEDN.getType());
			chatMessage.setStatus(MessageStatusEnum.SENDED.getStatus());
			messageMapper.insertOrUpdate(chatMessage);

			MessageSendDto messageSendDto = CopyTools.copy(chatMessage, MessageSendDto.class);
			// 发送给接受好友申请人
			messageHandler.sendMessage(messageSendDto);
			// 发送给接收人，联系人就是申请人，发送人为接受人
			messageSendDto.setMessageType(MessageTypeEnum.ADD_FRIEDN_SELF.getType());
			messageSendDto.setContactId(applyUserId);
			messageSendDto.setExtendData(contactUserInfo);
			messageHandler.sendMessage(messageSendDto);

		} else {
			//添加群组消息处理
			SessionUser sessionUser =new SessionUser();
			sessionUser.setUserId(applyUserId);
			sessionUser.setContactId(contactId);
			sessionUser.setSessionId(sessionId);
			GroupInfo groupInfo = this.groupInfoMapper.selectByGroupId(contactId);
			sessionUser.setContactName(groupInfo.getGroupName());
			this.sessionUserMapper.insert(sessionUser);
			
			
			//将成员入群消息发送的群组
			Info applyUserInfo =this.infoMapper.selectByUserId(applyUserId);
			String sendMessage =String.format(MessageTypeEnum.ADD_GROUP.getInitMessage(), applyUserInfo.getNickName());
			
			//增加session信息
			Session session =new Session();
			session.setSessionId(sessionId);
			session.setLastMessage(sendMessage);
			session.setLastRecieveTime(curDate.getTime());
			this.sessionMapper.insertOrUpdate(session);
			
			//增加聊天消息
			Message message =new Message();
			message.setSessionId(sessionId);
			message.setMessageType(MessageTypeEnum.ADD_GROUP.getType());
			message.setMessageContent(sendMessage);
			message.setContactId(contactId);
			message.setSendTime(curDate.getTime());
			message.setContactType(UserContactTypeEnum.GROUP.getType());
			message.setStatus(MessageStatusEnum.SENDED.getStatus());
			this.messageMapper.insert(message);
			
			//将群组添加到通道
			channelContexUtils.addUser2Group(applyUserId, groupInfo.getGroupId());
			//发送群消息
			MessageSendDto messageSendDto = CopyTools.copy(message, MessageSendDto.class);
			messageSendDto.setContactId(contactId);
			//获取群员数量
			ContactQuery contactQuery =new ContactQuery();
			contactQuery.setContactId(contactId);
			contactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
			Integer memberCount=this.contactMapper.selectCount(contactQuery);
			messageSendDto.setMemmberCount(memberCount);
			messageSendDto.setContactName(groupInfo.getGroupName());
			//发消息
			messageHandler.sendMessage(messageSendDto);
			
			
			
			
	
			

		}

//		MessageSendDto messageSendDto =new MessageSendDto();
//		messageSendDto.setSendUserId(applyUserId);
//		messageSendDto.setContactId(contactId);
//		messageSendDto.setMessageContent(MessageTypeEnum.ADD_FRIEDN.getInitMessage());
//		messageHandler.sendMessage(messageSendDto);

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addContact4Robot(String userId) {
		Date curDate = new Date();
		SysSettingDto sysSettingDto = redisComponent.getSysSetting();
		String contactId = sysSettingDto.getRobotUid();
		String contactName = sysSettingDto.getRobotNickName();
		String sendMessage = sysSettingDto.getRobotWelcome();
		sendMessage = StringTools.cleanHtmlTag(sendMessage);
		// 增加机器人好友
		Contact contact = new Contact();
		contact.setContactId(contactId);
		contact.setUserId(userId);
		contact.setContactType(UserContactTypeEnum.USER.getType());
		contact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
		contact.setCreateTime(curDate);
		contact.setLastUpdateTime(curDate);
		contactMapper.insert(contact);

		// 增加会话消息
		String sessionId = StringTools.getChatSessionId4User(new String[] { userId, contactId });
		Session session = new Session();
		session.setLastMessage(sendMessage);
		session.setSessionId(sessionId);
		session.setLastRecieveTime(curDate.getTime());
		this.sessionMapper.insert(session);
		// 增加联系人会话信息
		SessionUser sessionUser = new SessionUser();
		sessionUser.setContactId(contactId);
		sessionUser.setUserId(userId);
		sessionUser.setContactName(contactName);
		sessionUser.setSessionId(sessionId);
		this.sessionUserMapper.insert(sessionUser);

		// 增加聊天消息
		Message message = new Message();
		message.setSessionId(sessionId);
		message.setMessageType(MessageTypeEnum.CHAT.getType());
		message.setMessageContent(sendMessage);
		message.setSendUserId(sysSettingDto.getRobotUid());
		message.setSendUserNickName(contactName);
		message.setSendTime(curDate.getTime());
		message.setContactId(userId);
		message.setContactType(UserContactTypeEnum.USER.getType());
		message.setStatus(MessageStatusEnum.SENDED.getStatus());
		this.messageMapper.insert(message);

	}

}