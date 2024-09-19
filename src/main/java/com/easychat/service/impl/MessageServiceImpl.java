package com.easychat.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easychat.enums.DateTimePatternEnum;
import com.easychat.enums.MessageStatusEnum;
import com.easychat.enums.MessageTypeEnum;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.enums.UserContactStatusEnum;
import com.easychat.enums.UserContactTypeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.dto.MessageSendDto;
import com.easychat.dto.SysSettingDto;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.config.AppConfig;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.po.Contact;
import com.easychat.entity.po.Message;
import com.easychat.entity.po.Session;
import com.easychat.entity.query.ContactQuery;
import com.easychat.entity.query.MessageQuery;
import com.easychat.entity.query.SessionQuery;
import com.easychat.entity.query.SimplePage;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.service.MessageService;
import com.easychat.utils.CopyTools;
import com.easychat.utils.DateUtil;
import com.easychat.utils.StringTools;
import com.easychat.websocket.MessageHandler;
import jodd.util.ArraysUtil;

import com.easychat.mappers.ContactMapper;
import com.easychat.mappers.MessageMapper;
import com.easychat.mappers.SessionMapper;
import com.easychat.redis.RedisComponent;


/**
 * 聊天信息表ServiceImpl
 * 
 * @auther: 系统
 * @date: 2024-07-28 17:23
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
	
	private static final Logger logger =LoggerFactory.getLogger(MessageService.class);

	@Resource
	private MessageMapper<Message, MessageQuery> messageMapper;

	@Resource
	private RedisComponent redisComponent;

	@Resource
	private SessionMapper<Session, SessionQuery> sessionMapper;

	@Resource
	private MessageHandler messageHandler;
	
	@Resource
	private AppConfig appConfig;
	
	@Resource
	private ContactMapper< Contact, ContactQuery> contactMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<Message> findListByParam(MessageQuery query) {
		return this.messageMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(MessageQuery query) {
		return this.messageMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<Message> findListByPage(MessageQuery query) {
		Integer count = this.findCountByParam(query);
		SimplePage page = new SimplePage(query.getPageNo(), query.getPageSize(), count);
		query.setSimplePage(page);
		List<Message> list = this.findListByParam(query);
		PaginationResultVO<Message> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(),
				page.getCountPage(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(Message bean) {
		return this.messageMapper.insert(bean);
	}

	/**
	 * 新增批量
	 */
	public Integer addBatch(List<Message> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.messageMapper.insertBatch(listBean);
	}

	/**
	 * 新增批量或修改
	 */
	public Integer addOrUpdateBatch(List<Message> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.messageMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据MessageId查询
	 */
	public Message selectByMessageId(Long messageId) {
		return this.messageMapper.selectByMessageId(messageId);
	}

	/**
	 * 根据MessageId更新
	 */
	public Integer updateByMessageId(Message bean, Long messageId) {
		return this.messageMapper.updateByMessageId(bean, messageId);
	}

	/**
	 * 根据MessageId删除
	 */
	public Integer deleteByMessageId(Long messageId) {
		return this.messageMapper.deleteByMessageId(messageId);
	}

	/**
	 * 根据SessionId查询
	 */
	public Message selectBySessionId(String sessionId) {
		return this.messageMapper.selectBySessionId(sessionId);
	}

	/**
	 * 根据SessionId更新
	 */
	public Integer updateBySessionId(Message bean, String sessionId) {
		return this.messageMapper.updateBySessionId(bean, sessionId);
	}

	/**
	 * 根据SessionId删除
	 */
	public Integer deleteBySessionId(String sessionId) {
		return this.messageMapper.deleteBySessionId(sessionId);
	}

	/**
	 * 根据ContactId查询
	 */
	public Message selectByContactId(String contactId) {
		return this.messageMapper.selectByContactId(contactId);
	}

	/**
	 * 根据ContactId更新
	 */
	public Integer updateByContactId(Message bean, String contactId) {
		return this.messageMapper.updateByContactId(bean, contactId);
	}

	/**
	 * 根据ContactId删除
	 */
	public Integer deleteByContactId(String contactId) {
		return this.messageMapper.deleteByContactId(contactId);
	}

	/**
	 * 根据SendUserId查询
	 */
	public Message selectBySendUserId(String sendUserId) {
		return this.messageMapper.selectBySendUserId(sendUserId);
	}

	/**
	 * 根据SendUserId更新
	 */
	public Integer updateBySendUserId(Message bean, String sendUserId) {
		return this.messageMapper.updateBySendUserId(bean, sendUserId);
	}

	/**
	 * 根据SendUserId删除
	 */
	public Integer deleteBySendUserId(String sendUserId) {
		return this.messageMapper.deleteBySendUserId(sendUserId);
	}

	/**
	 * 根据SendTime查询
	 */
	public Message selectBySendTime(Long sendTime) {
		return this.messageMapper.selectBySendTime(sendTime);
	}

	/**
	 * 根据SendTime更新
	 */
	public Integer updateBySendTime(Message bean, Long sendTime) {
		return this.messageMapper.updateBySendTime(bean, sendTime);
	}

	/**
	 * 根据SendTime删除
	 */
	public Integer deleteBySendTime(Long sendTime) {
		return this.messageMapper.deleteBySendTime(sendTime);
	}

	/**
	 *
	 * @param message
	 * @param tokenUserInfoDto
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public MessageSendDto saveMessage(Message message, TokenUserInfoDto tokenUserInfoDto) {
		// 判断消息是否为机器人回复
		if (!Constants.ROBOT_UID.equals(tokenUserInfoDto.getUserId())) {
			// 判断联系人id是否在好友列表缓存中
			List<String> contactList = redisComponent.getUserContactList(tokenUserInfoDto.getUserId());
			if (!contactList.contains(message.getContactId())) {
				// 根据联系人类别发送不同的错误信息
				UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getByprefix(message.getContactId());
				if (contactTypeEnum.USER.equals(contactTypeEnum)) {
					throw new BusinessException(ResponseCodeEnum.CODE_902);
				} else {
					throw new BusinessException(ResponseCodeEnum.CODE_903);
				}
			}
		}
		String sessionId = null;
		String sendUserId = tokenUserInfoDto.getUserId();
		String contactId = message.getContactId();

		UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getByprefix(contactId);

		if (UserContactTypeEnum.USER == contactTypeEnum) {
			sessionId = StringTools.getChatSessionId4User(new String[] { sendUserId, contactId });
		} else {
			sessionId = StringTools.getChatSessionId4Group(contactId);
		}
		message.setSessionId(sessionId);
		MessageTypeEnum messageTypeEnum = MessageTypeEnum.getByType(message.getMessageType());
		if (messageTypeEnum == null || !ArraysUtil.contains(
				new Integer[] { MessageTypeEnum.CHAT.getType(), MessageTypeEnum.MEDIA_CHAT.getType() },
				message.getMessageType())) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		Long curTime = System.currentTimeMillis();
		message.setSendTime(curTime);
		Integer status = MessageTypeEnum.MEDIA_CHAT == messageTypeEnum ? MessageStatusEnum.SENDING.getStatus()
				: MessageStatusEnum.SENDED.getStatus();
		message.setStatus(status);
		String messageContent = StringTools.cleanHtmlTag(message.getMessageContent());
		message.setMessageContent(messageContent);
		// 更新会话
		Session session = new Session();
		session.setLastMessage(messageContent);
		if (UserContactTypeEnum.GROUP == contactTypeEnum) {
			session.setLastMessage(tokenUserInfoDto.getNickName() + ":" + messageContent);
		}
		session.setLastRecieveTime(curTime);
		this.sessionMapper.updateBySessionId(session, sessionId);
		// 记录消息表
		message.setContactType(contactTypeEnum.getType());
		messageMapper.insert(message);
		MessageSendDto messageSendDto = CopyTools.copy(message, MessageSendDto.class);
		// 与机器人聊天
		if (Constants.ROBOT_UID.equals(contactId)) {
			SysSettingDto sysSettingDto = redisComponent.getSysSetting();
			TokenUserInfoDto robot = new TokenUserInfoDto();
			robot.setUserId(sysSettingDto.getRobotUid());
			robot.setNickName(sysSettingDto.getRobotNickName());
			Message robotMessage = new Message();
			// 可调用ai接口
			robotMessage.setContactId(sendUserId);
			robotMessage.setMessageContent("我只是一个机器人无法识别你的消息");
			robotMessage.setMessageType(MessageTypeEnum.CHAT.getType());
			saveMessage(robotMessage, robot);
		} else {
			// 发消息
			messageHandler.sendMessage(messageSendDto);
		}
		return messageSendDto;
	}

	@Override
	public void saveMessageFile(String userId, Long messageId, MultipartFile file, MultipartFile coverFile) {
		Message message = messageMapper.selectByMessageId(messageId);
		if (message == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if (!userId.equals(message.getSendUserId())) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		SysSettingDto sysSettingDto = redisComponent.getSysSetting();
		String fileSuffix = StringTools.getFileSuffix(file.getOriginalFilename());
		if (!StringTools.isEmpty(fileSuffix)
				&& ArraysUtil.contains(Constants.IMAGE_SUFFIX_LIST, fileSuffix.toLowerCase())
				&& file.getSize() > sysSettingDto.getMaxImageSize() * Constants.FILE_SIZE_MB) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		} else if (!StringTools.isEmpty(fileSuffix)
				&& ArraysUtil.contains(Constants.VIDEO_SUFFIX_LIST, fileSuffix.toLowerCase())
				&& file.getSize() > sysSettingDto.getMaxVideoSize() * Constants.FILE_SIZE_MB) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		} else if (!StringTools.isEmpty(fileSuffix)
				&& !ArraysUtil.contains(Constants.IMAGE_SUFFIX_LIST, fileSuffix.toLowerCase())
				&& !ArraysUtil.contains(Constants.VIDEO_SUFFIX_LIST, fileSuffix.toLowerCase())
				&& file.getSize() > sysSettingDto.getMaxFileSize() * Constants.FILE_SIZE_MB) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		String fileName =file.getOriginalFilename();
		String fileExtName=StringTools.getFileSuffix(fileName);
		String fileRealName=messageId+fileExtName;
		//分目录保存文件
		String month=DateUtil.format(new Date(message.getSendTime()), DateTimePatternEnum.YYYYMM.getPattern());
		java.io.File folder=new File(appConfig.getProjectFloder()+Constants.FILE_FOLDER_FILE+month);
		if (!folder.exists()) {
			folder.mkdir();
		}
		File uploadFile=new File(folder.getPath() +"/"+fileRealName);
		try {
			file.transferTo(uploadFile);
			coverFile.transferTo(new File(uploadFile.getPath()+Constants.COVER_IMAGE_SUFFIX));
			
		} catch (Exception e) {
			logger.error("上传文件失败",e);
			throw new BusinessException("上传文件失败");
		}
		Message uploadInfo=new Message();
		uploadInfo.setStatus(MessageStatusEnum.SENDED.getStatus());
		this.messageMapper.updateByMessageId(uploadInfo, messageId);
		
		MessageSendDto messageSendDto =new MessageSendDto();
		messageSendDto.setStatus(MessageStatusEnum.SENDED.getStatus());
		messageSendDto.setMessageId(messageId);
		messageSendDto.setMessageType(MessageTypeEnum.FILE_UPLOAD.getType());
		messageSendDto.setContactId(message.getContactId());
		messageHandler.sendMessage(messageSendDto);
	}

	@Override
	public File downloadFile(TokenUserInfoDto tokenUserInfoDto, Long messageId, boolean showCover) {
		Message message = messageMapper.selectByMessageId(messageId);
		String contactId=message.getContactId();
		UserContactTypeEnum contactTypeEnum =UserContactTypeEnum.getByprefix(contactId);
		if (contactTypeEnum==UserContactTypeEnum.USER&&
			!tokenUserInfoDto.getUserId().equals(message.getContactId())) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		//判断是否是群成员，不是群成员无法下载文件
		if (contactTypeEnum.GROUP.equals(contactTypeEnum)) {
			ContactQuery contactQuery =new ContactQuery();
			contactQuery.setUserId(tokenUserInfoDto.getUserId());
			contactQuery.setContactType(contactTypeEnum.getType());
			contactQuery.setContactId(contactId);
			contactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
			Integer contactCount =contactMapper.selectCount(contactQuery);
			if (contactCount==0) {
				throw new BusinessException(ResponseCodeEnum.CODE_600);
			}
		}
		
		String month=DateUtil.format(new Date(message.getSendTime()), DateTimePatternEnum.YYYYMM.getPattern());
		java.io.File folder=new File(appConfig.getProjectFloder()+Constants.FILE_FOLDER_FILE+month);
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		String fileName =message.getFileName();
		String fileExtName=StringTools.getFileSuffix(fileName);
		String fileRealName=messageId+fileExtName;
		if (showCover) {
			fileRealName=fileRealName +Constants.COVER_IMAGE_SUFFIX;
		}
		File file =new File(folder.getPath()+"/"+fileRealName);
		if (!file.exists()) {
			logger.info("文件不存在:{}",messageId);
			throw new BusinessException(ResponseCodeEnum.CODE_602);
		}
		return file;
	}

}
