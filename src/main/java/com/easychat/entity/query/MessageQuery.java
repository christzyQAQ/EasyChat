package com.easychat.entity.query;

import java.util.List;

/**
 * 聊天信息表查询对象
 * @auther: 系统
 * @date: 2024-07-28 17:22
 */
public class MessageQuery extends BaseQuery {

	/**
	 * 消息自增id
	 */
	private Long messageId;

	/**
	 * 会话id
	 */
	private String sessionId;

	/**
	 * 消息类型
	 */
	private Integer messageType;

	/**
	 * 消息内容
	 */
	private String messageContent;

	/**
	 * 发送人id
	 */
	private String sendUserId;

	/**
	 * 发送人昵称
	 */
	private String sendUserNickName;

	/**
	 * 发送时间
	 */
	private Long sendTime;

	/**
	 * 联系人id
	 */
	private String contactId;

	/**
	 * 联系类型：0：单聊：1：群聊
	 */
	private Integer contactType;

	/**
	 * 文件大小
	 */
	private Long fileSize;

	/**
	 * 文件名字
	 */
	private String fileName;

	/**
	 * 文件类型
	 */
	private Integer fileType;

	/**
	 * 状态：0：正在发送1:已发送
	 */
	private Integer status;


	private String sessionIdFuzzy;

	private String messageContentFuzzy;

	private String sendUserIdFuzzy;

	private String sendUserNickNameFuzzy;

	private String contactIdFuzzy;

	private String fileNameFuzzy;
	
	private List<String>  contactIdList;
	
	private Long lastReciveTime;


	public Long getLastReciveTime() {
		return lastReciveTime;
	}
	public void setLastReciveTime(Long lastRevieveTime) {
		this.lastReciveTime = lastRevieveTime;
	}
	public List<String> getContactIdList() {
		return contactIdList;
	}
	public void setContactIdList(List<String> contactIdList) {
		this.contactIdList = contactIdList;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Long getMessageId() {
		return messageId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionId() {
		return sessionId;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getMessageContent() {
		return messageContent;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserNickName(String sendUserNickName) {
		this.sendUserNickName = sendUserNickName;
	}
	public String getSendUserNickName() {
		return sendUserNickName;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	public Long getSendTime() {
		return sendTime;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactId() {
		return contactId;
	}

	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}
	public Integer getContactType() {
		return contactType;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileName() {
		return fileName;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	public Integer getFileType() {
		return fileType;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return status;
	}


	public void setSessionIdFuzzy(String sessionIdFuzzy) {
		this.sessionIdFuzzy = sessionIdFuzzy;
	}
	public String getSessionIdFuzzy() {
		return sessionIdFuzzy;
	}

	public void setMessageContentFuzzy(String messageContentFuzzy) {
		this.messageContentFuzzy = messageContentFuzzy;
	}
	public String getMessageContentFuzzy() {
		return messageContentFuzzy;
	}

	public void setSendUserIdFuzzy(String sendUserIdFuzzy) {
		this.sendUserIdFuzzy = sendUserIdFuzzy;
	}
	public String getSendUserIdFuzzy() {
		return sendUserIdFuzzy;
	}

	public void setSendUserNickNameFuzzy(String sendUserNickNameFuzzy) {
		this.sendUserNickNameFuzzy = sendUserNickNameFuzzy;
	}
	public String getSendUserNickNameFuzzy() {
		return sendUserNickNameFuzzy;
	}

	public void setContactIdFuzzy(String contactIdFuzzy) {
		this.contactIdFuzzy = contactIdFuzzy;
	}
	public String getContactIdFuzzy() {
		return contactIdFuzzy;
	}

	public void setFileNameFuzzy(String fileNameFuzzy) {
		this.fileNameFuzzy = fileNameFuzzy;
	}
	public String getFileNameFuzzy() {
		return fileNameFuzzy;
	}


}