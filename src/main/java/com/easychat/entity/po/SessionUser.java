package com.easychat.entity.po;

import java.io.Serializable;
/**
 * 会话用户
 * @auther: 系统
 * @date: 2024-07-28 17:23
 */
public class SessionUser implements Serializable {

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 联系人id
	 */
	private String contactId;

	/**
	 * 会话id
	 */
	private String sessionId;

	/**
	 * 联系人名称
	 */
	private String contactName;
	
	private String lastMessage;
	
	private Integer memberCount;
	
	
	public Integer getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}
	public String getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	public Long getLastRecieveTime() {
		return lastRecieveTime;
	}
	public void setLastRecieveTime(Long lastRecieveTime) {
		this.lastRecieveTime = lastRecieveTime;
	}

	private Long lastRecieveTime;


	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactId() {
		return contactId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactName() {
		return contactName;
	}

	@Override
	public String toString() {
		return "用户id:" + (userId == null ? "空" : userId) + ",联系人id:" + (contactId == null ? "空" : contactId) + ",会话id:" + (sessionId == null ? "空" : sessionId) + ",联系人名称:" + (contactName == null ? "空" : contactName);
	}

}