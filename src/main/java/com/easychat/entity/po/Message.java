package com.easychat.entity.po;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 聊天信息表
 * @auther: 系统
 * @date: 2024-07-28 17:22
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -268121304076379350L;

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
	@JsonIgnore
	private Integer status;


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

	@Override
	public String toString() {
		return "消息自增id:" + (messageId == null ? "空" : messageId) + ",会话id:" + (sessionId == null ? "空" : sessionId) + ",消息类型:" + (messageType == null ? "空" : messageType) + ",消息内容:" + (messageContent == null ? "空" : messageContent) + ",发送人id:" + (sendUserId == null ? "空" : sendUserId) + ",发送人昵称:" + (sendUserNickName == null ? "空" : sendUserNickName) + ",发送时间:" + (sendTime == null ? "空" : sendTime) + ",联系人id:" + (contactId == null ? "空" : contactId) + ",联系类型：0：单聊：1：群聊:" + (contactType == null ? "空" : contactType) + ",文件大小:" + (fileSize == null ? "空" : fileSize) + ",文件名字:" + (fileName == null ? "空" : fileName) + ",文件类型:" + (fileType == null ? "空" : fileType) + ",状态：0：正在发送1:已发送:" + (status == null ? "空" : status);
	}

}