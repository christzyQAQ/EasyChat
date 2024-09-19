package com.easychat.service.impl;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.easychat.enums.MessageTypeEnum;
import com.easychat.enums.PageSize;
import com.easychat.enums.UserContactStatusEnum;
import com.easychat.enums.UserContactTypeEnum;
import com.easychat.enums.UserSessionEnum;
import com.easychat.dto.MessageSendDto;
import com.easychat.entity.po.Contact;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.po.Info;
import com.easychat.entity.po.Session;
import com.easychat.entity.po.SessionUser;
import com.easychat.entity.query.ContactQuery;
import com.easychat.entity.query.SessionUserQuery;
import com.easychat.entity.query.SimplePage;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.service.SessionUserService;
import com.easychat.websocket.MessageHandler;
import com.easychat.mappers.ContactMapper;
import com.easychat.mappers.SessionMapper;
import com.easychat.mappers.SessionUserMapper;

/**
 * 会话用户ServiceImpl
 * 
 * @auther: 系统
 * @date: 2024-07-28 17:23
 */
@Service("sessionUserService")
public class SessionUserServiceImpl implements SessionUserService {

	@Resource
	private SessionUserMapper<SessionUser, SessionUserQuery> sessionUserMapper;

	@Resource
	private MessageHandler messageHandler;

	@Resource
	private ContactMapper<Contact, ContactQuery> contactMapper;
	
	@Resource
	private SessionMapper sessionMapper; 

	/**
	 * 根据条件查询列表
	 */
	public List<SessionUser> findListByParam(SessionUserQuery query) {
		return this.sessionUserMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(SessionUserQuery query) {
		return this.sessionUserMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<SessionUser> findListByPage(SessionUserQuery query) {
		Integer count = this.findCountByParam(query);
		SimplePage page = new SimplePage(query.getPageNo(), query.getPageSize(), count);
		query.setSimplePage(page);
		List<SessionUser> list = this.findListByParam(query);
		PaginationResultVO<SessionUser> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(),
				page.getCountPage(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(SessionUser bean) {
		return this.sessionUserMapper.insert(bean);
	}

	/**
	 * 新增批量
	 */
	public Integer addBatch(List<SessionUser> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.sessionUserMapper.insertBatch(listBean);
	}

	/**
	 * 新增批量或修改
	 */
	public Integer addOrUpdateBatch(List<SessionUser> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.sessionUserMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据UserIdAndContactId查询
	 */
	public SessionUser selectByUserIdAndContactId(String userId, String contactId) {
		return this.sessionUserMapper.selectByUserIdAndContactId(userId, contactId);
	}

	/**
	 * 根据UserIdAndContactId更新
	 */
	public Integer updateByUserIdAndContactId(SessionUser bean, String userId, String contactId) {
		return this.sessionUserMapper.updateByUserIdAndContactId(bean, userId, contactId);
	}

	/**
	 * 根据UserIdAndContactId删除
	 */
	public Integer deleteByUserIdAndContactId(String userId, String contactId) {
		return this.sessionUserMapper.deleteByUserIdAndContactId(userId, contactId);
	}

	/**
	 * 根据UserId查询
	 */
	public SessionUser selectByUserId(String userId) {
		return this.sessionUserMapper.selectByUserId(userId);
	}

	/**
	 * 根据UserId更新
	 */
	public Integer updateByUserId(SessionUser bean, String userId) {
		return this.sessionUserMapper.updateByUserId(bean, userId);
	}

	/**
	 * 根据UserId删除
	 */
	public Integer deleteByUserId(String userId) {
		return this.sessionUserMapper.deleteByUserId(userId);
	}

	/**
	 * 根据SessionId查询
	 */
	public SessionUser selectBySessionId(String sessionId) {
		return this.sessionUserMapper.selectBySessionId(sessionId);
	}

	/**
	 * 根据SessionId更新
	 */
	public Integer updateBySessionId(SessionUser bean, String sessionId) {
		return this.sessionUserMapper.updateBySessionId(bean, sessionId);
	}

	/**
	 * 根据SessionId删除
	 */
	public Integer deleteBySessionId(String sessionId) {
		return this.sessionUserMapper.deleteBySessionId(sessionId);
	}

	@Override
	/**
	 * 更新用户冗余信息
	 */
	public void updateRedundanceInfo(String contactNameUpdate, String contactId) {

		SessionUser updateInfo = new SessionUser();
		updateInfo.setContactName(contactNameUpdate);
		SessionUserQuery sessionUserQuery = new SessionUserQuery();
		sessionUserQuery.setContactId(contactId);
		this.sessionUserMapper.updateByParam(updateInfo, sessionUserQuery);
//		this.sessionUserMapper.updateByContactId(updateInfo, groupinfo.getGroupId());

		// 修改昵称发送ws信息 实现实时更新
		UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getByprefix(contactId);
		if (contactTypeEnum == UserContactTypeEnum.GROUP) {
			MessageSendDto messageSendDto = new MessageSendDto();
			messageSendDto.setContactType(UserContactTypeEnum.getByprefix(contactId).getType());
			messageSendDto.setContactId(contactId);
			messageSendDto.setExtendData(contactNameUpdate);
			messageSendDto.setMessageType(MessageTypeEnum.CONTACT_NAME_UPDATE.getType());
			messageHandler.sendMessage(messageSendDto);
		} else {
			ContactQuery contactQuery = new ContactQuery();
			contactQuery.setContactType(contactTypeEnum.getType());
			contactQuery.setContactId(contactId);
			contactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
			List<Contact> contactList = contactMapper.selectList(contactQuery);
			//给用户所有好友发送ws信息更新自己在好友的昵称
			for (Contact contact : contactList) {
				MessageSendDto messageSendDto = new MessageSendDto();
				messageSendDto.setContactType(contactTypeEnum.getType());
				messageSendDto.setContactId(contact.getUserId());
				messageSendDto.setExtendData(contactNameUpdate);
				messageSendDto.setMessageType(MessageTypeEnum.CONTACT_NAME_UPDATE.getType());
				messageSendDto.setSendUserId(contactId);
				messageSendDto.setSendUserNickName(contactNameUpdate);
				messageHandler.sendMessage(messageSendDto);
			}
		}

	}

	@Override
	public void pinSession(String userId, String contactId) {	
	String sessionId=sessionUserMapper.selectByUserIdAndContactId(userId, contactId).getSessionId();
	Session session =new Session();
	session.setIsPinned(UserSessionEnum.PIN.getStatus());
	sessionMapper.updateBySessionId(session, sessionId);	
	}
	
	@Override
	public void unPinSession(String userId, String contactId) {	
	String sessionId=sessionUserMapper.selectByUserIdAndContactId(userId, contactId).getSessionId();
	Session session =new Session();
	session.setIsPinned(UserSessionEnum.UNPIN.getStatus());
	sessionMapper.updateBySessionId(session, sessionId);	
	}
	
	

}