package com.easychat.service.impl;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easychat.enums.GroupStatusEnum;
import com.easychat.enums.MessageStatusEnum;
import com.easychat.enums.MessageTypeEnum;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.enums.UserContactStatusEnum;
import com.easychat.enums.UserContactTypeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.dto.MessageSendDto;
import com.easychat.dto.SysSettingDto;
import com.easychat.entity.config.AppConfig;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.po.Contact;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.po.Message;
import com.easychat.entity.po.Session;
import com.easychat.entity.po.SessionUser;
import com.easychat.entity.query.ContactQuery;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.entity.query.MessageQuery;
import com.easychat.entity.query.SessionQuery;
import com.easychat.entity.query.SessionUserQuery;
import com.easychat.entity.query.SimplePage;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.service.GroupInfoService;
import com.easychat.service.SessionUserService;
import com.easychat.utils.StringTools;
import com.easychat.websocket.ChannelContextUtils;
import com.easychat.websocket.MessageHandler;
import com.easychat.mappers.ContactMapper;
import com.easychat.mappers.GroupInfoMapper;
import com.easychat.mappers.MessageMapper;
import com.easychat.mappers.SessionMapper;
import com.easychat.mappers.SessionUserMapper;
import com.easychat.redis.RedisComponent;

/**
 * ServiceImpl
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
@Service("groupInfoService")
public class GroupInfoServiceImpl implements GroupInfoService {

	@Resource
	private GroupInfoMapper<GroupInfo, GroupInfoQuery> groupInfoMapper;
	
	@Resource 
	private ContactMapper<Contact, ContactQuery> contactMapper;
	
	@Resource
	private RedisComponent redisComponent;
	
	@Resource
	private AppConfig appConfig;
	
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
	private SessionUserService sessionUserService;
	

	/**
	 * 根据条件查询列表
	 */
	public List<GroupInfo> findListByParam(GroupInfoQuery query) {
		return this.groupInfoMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(GroupInfoQuery query) {
		return this.groupInfoMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<GroupInfo> findListByPage(GroupInfoQuery query) {
		Integer count = this.findCountByParam(query);
		SimplePage page = new SimplePage(query.getPageNo(), query.getPageSize(), count);
		query.setSimplePage(page);
		List<GroupInfo> list = this.findListByParam(query);
		PaginationResultVO<GroupInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getCountPage(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(GroupInfo bean) {
		return this.groupInfoMapper.insert(bean);
	}

	/**
	 * 新增批量
	 */
	public Integer addBatch(List<GroupInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.groupInfoMapper.insertBatch(listBean);
	}

	/**
	 * 新增批量或修改
	 */
	public Integer addOrUpdateBatch(List<GroupInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.groupInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据GroupId查询
	 */
	public GroupInfo selectByGroupId(String groupId) {
		return this.groupInfoMapper.selectByGroupId(groupId);
	}

	/**
	 * 根据GroupId更新
	 */
	public Integer updateByGroupId(GroupInfo bean, String groupId) {
		return this.groupInfoMapper.updateByGroupId(bean, groupId);
	}

	/**
	 * 根据GroupId删除
	 */
	public Integer deleteByGroupId(String groupId) {
		return this.groupInfoMapper.deleteByGroupId(groupId);
	}

	
	
	/**
	 * 创建群组
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveGroup(GroupInfo groupinfo, MultipartFile avatarFile, MultipartFile avatarCover) throws IOException {
		Date curDate=new Date();
		//新增
		if (StringTools.isEmpty(groupinfo.getGroupId())) {
			GroupInfoQuery groupInfoQuery =new GroupInfoQuery();
			groupInfoQuery.setGroupOwnerId(groupinfo.getGroupOwnerId());
			Integer count=this.groupInfoMapper.selectCount(groupInfoQuery);
			SysSettingDto sysSettingDto =redisComponent.getSysSetting();
			if(count>sysSettingDto.getMaxGroupCount()) {
				throw new BusinessException("最多只能创建"+sysSettingDto.getMaxGroupCount()+"个群聊");								
			}
			if(avatarFile==null) {
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}
			
			groupinfo.setCreateTime(curDate);			
			groupinfo.setGroupId(StringTools.getGroupId());
			this.groupInfoMapper.insert(groupinfo);
		
			//将群主添加为联系人
			Contact contact= new Contact();
			contact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
			contact.setStatus(UserContactTypeEnum.GROUP.getType());
			contact.setContactId(groupinfo.getGroupId());
			contact.setUserId(groupinfo.getGroupOwnerId());
			contact.setCreateTime(curDate);
			contact.setLastUpdateTime(curDate);
			this.contactMapper.insert(contact);
					
			//创建会话条
			String sessionId=StringTools.getChatSessionId4Group(groupinfo.getGroupId());
			Session session=new Session();
			session.setSessionId(sessionId);
			session.setLastMessage(MessageTypeEnum.GROUP_CREAT.getInitMessage());
			session.setLastRecieveTime(curDate.getTime());
			this.sessionMapper.insertOrUpdate(session);
			
			//创建会话框
			SessionUser sessionUser =new SessionUser();
			sessionUser.setUserId(groupinfo.getGroupOwnerId());
			sessionUser.setContactId(groupinfo.getGroupId());
			sessionUser.setContactName(groupinfo.getGroupName());
			sessionUser.setSessionId(sessionId);
			this.sessionUserMapper.insert(sessionUser);
			
			//创建群会话消息
			Message message =new Message();
			message.setSessionId(sessionId);
			message.setContactId(groupinfo.getGroupId());
			message.setMessageType(MessageTypeEnum.GROUP_CREAT.getType());
			message.setMessageContent(MessageTypeEnum.GROUP_CREAT.getInitMessage());
			message.setSendTime(curDate.getTime());
			message.setContactType(UserContactTypeEnum.GROUP.getType());
			message.setStatus(MessageStatusEnum.SENDED.getStatus());
			this.messageMapper.insert(message);
			
			//添加群组到群主的联系人
			redisComponent.addUserContact(groupinfo.getGroupOwnerId(),groupinfo.getGroupId());
			//将联系人的通道添加到群组通道
			channelContexUtils.addUser2Group(groupinfo.getGroupOwnerId(),groupinfo.getGroupId());
			
			//发送ws消息
			sessionUser.setLastMessage(MessageTypeEnum.GROUP_CREAT.getInitMessage());
			sessionUser.setLastRecieveTime(curDate.getTime());
			sessionUser.setMemberCount(1);
			
			MessageSendDto messageSendDto =new MessageSendDto();
			messageSendDto.setExtendData(sessionUser);
			messageSendDto.setLastMessage(sessionUser.getLastMessage());
			messageHandler.sendMessage(messageSendDto);
						
			//TODO 发送消息
		}else {
			GroupInfo dbInfo =this.groupInfoMapper.selectByGroupId(groupinfo.getGroupId());
			if (!dbInfo.getGroupOwnerId().equals(groupinfo.getGroupOwnerId())) {
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}	
			this.groupInfoMapper.updateByGroupId(groupinfo, groupinfo.getGroupId());
			// 更新相关表的冗余信息				
			String contactNameUpdate=null;
			if (!dbInfo.getGroupName().equals(groupinfo.getGroupName())) {				
				contactNameUpdate=groupinfo.getGroupName();
			}
			if (contactNameUpdate==null) {
				return;
			}			
			sessionUserService.updateRedundanceInfo(contactNameUpdate, groupinfo.getGroupId());
			
			
		}
		
		if (avatarFile==null) {
			return;			
		}
		
	try {
		String baseFolder= appConfig.getProjectFloder()+ Constants.FILE_FOLDER_FILE;
		System.out.println("基础路径为："+ baseFolder);
		File targetFileFolder= new File(baseFolder + Constants.FILE_FOLDER_AVATAR_NAME);
		System.out.println("目标路径为："+ targetFileFolder);
		 if (!targetFileFolder.exists()) {
			 targetFileFolder.mkdirs();
		}
		// 构建文件路径
		    String filePath = new File(targetFileFolder, groupinfo.getGroupId() + Constants.IMAGE_SUFFIX).getPath();
		    System.out.println("文件路径为："+ filePath);
		    String coverFilePath = filePath + Constants.COVER_IMAGE_SUFFIX;
		    System.out.println("头像封面路径为："+ coverFilePath);

		    
		    // 将头像文件保存到指定路径
		    avatarFile.transferTo(new File(filePath));
		    avatarCover.transferTo(new File(coverFilePath));
	} catch (IOException e) {
		e.printStackTrace();
		 throw new RuntimeException("Failed to save avatar file", e);
	}
		
		
		
		
	}

	@Override
	public void dissolutionGroup(String groupOwnerId, String groupId) {
		GroupInfo dbInfo = groupInfoMapper.selectByGroupId(groupId);
		if(dbInfo.getGroupId()!=groupOwnerId) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		//删除群组
		GroupInfo updateInfo = new GroupInfo();
		updateInfo.setStatus(GroupStatusEnum.DISSOLUTION.getStatus());
		this.groupInfoMapper.updateByGroupId(updateInfo, groupId);
		
		//更新联系人信息
		ContactQuery contactQuery =new ContactQuery();
		contactQuery.setContactId(groupId);
		contactQuery.setContactType(UserContactTypeEnum.GROUP.getType());
		
		Contact updadeContact=new Contact();
		updadeContact.setStatus(UserContactStatusEnum.DEL.getStatus());
		contactMapper.updateByQuery(updadeContact, contactQuery);
		
		//TODO 移除相关群员的联系人缓存		
		//TODO 发消息：1、更新会话消息，2：记录群消息，3：发送群解散通知消息
		
	}

}