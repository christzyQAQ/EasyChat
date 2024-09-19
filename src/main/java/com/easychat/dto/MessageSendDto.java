package com.easychat.dto;

import java.io.Serializable;

import com.easychat.utils.StringTools;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 29, 20249:52:13 AM
* @ClassName:MessageSendDto.java

*/

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageSendDto<T> implements Serializable {


	private static final long serialVersionUID = 8150955387350471333L;
	//消息id
	private Long messageId;
	//会话id
	private String sessionId;
	//发送人id
	private String sendUserId;
	//发送人昵称
	private String sendUserNickName;
	//联系人id
	private String contactId;
	//联系人昵称
	private String contactName;
	//消息内容
	private String messageContent;
	//最后的消息
	private String lastMessage;
	//消息类型
	private Integer messageType;
	//发送时间
	private Long sendTime;
	//联系人类型
	private Integer contactType;
	//拓展信息
	private T extendData;
	//消息状态：0：发送中1：已发送 对于文件是异步上传y用状态处理
	private Integer status;
	
	
	//文件信息
	private Long fileSize;
	private String fileName;
	private Integer fileType;
	
	//群员数
	private Integer memmberCount;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendUserNickName() {
		return sendUserNickName;
	}

	public void setSendUserNickName(String sendUserNickName) {
		this.sendUserNickName = sendUserNickName;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getLastMessage() {
		if (StringTools.isEmpty(lastMessage)) {
			return messageContent;
		}
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getContactType() {
		return contactType;
	}

	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}

	public T getExtendData() {
		return extendData;
	}

	public void setExtendData(T extendData) {
		this.extendData = extendData;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Integer getMemmberCount() {
		return memmberCount;
	}

	public void setMemmberCount(Integer memmberCount) {
		this.memmberCount = memmberCount;
	}
	
	
	
}
