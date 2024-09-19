package com.easychat.entity.po;

import java.io.Serializable;
/**
 * 
 * @auther: christzy
 * @date: 2024-09-19 10:18
 */
public class Session implements Serializable {

	/**
	 * 会话id
	 */
	private String sessionId;

	/**
	 * 最后接收的消息
	 */
	private String lastMessage;

	/**
	 * 最后接收消息的时间（毫秒）
	 */
	private Long lastRecieveTime;

	/**
	 * 0:默认不置顶；1：置顶会话
	 */
	private Integer isPinned;


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
	public String getLastMessage() {
		return lastMessage;
	}
	public void setLastRecieveTime(Long lastRecieveTime) {
		this.lastRecieveTime = lastRecieveTime;
	}
	public Long getLastRecieveTime() {
		return lastRecieveTime;
	}
	public void setIsPinned(Integer isPinned) {
		this.isPinned = isPinned;
	}
	public Integer getIsPinned() {
		return isPinned;
	}

	@Override
	public String toString() {
		return "会话id:" + (sessionId == null ? "空" : sessionId) + ",最后接收的消息:" + (lastMessage == null ? "空" : lastMessage) + ",最后接收消息的时间（毫秒）:" + (lastRecieveTime == null ? "空" : lastRecieveTime) + ",0:默认不置顶；1：置顶会话:" + (isPinned == null ? "空" : isPinned);
	}

}