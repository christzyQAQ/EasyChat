package com.easychat.entity.po;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @auther: 系统
 * @date: 2024-07-28 17:23
 */
public class Session implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7335209494658098894L;

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


	public Long getLastRecieveTime() {
		return lastRecieveTime;
	}
	public void setLastRecieveTime(Long lastRecieveTime) {
		this.lastRecieveTime = lastRecieveTime;
	}
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


	@Override
	public String toString() {
		return "会话id:" + (sessionId == null ? "空" : sessionId) + ",最后接收的消息:" + (lastMessage == null ? "空" : lastMessage) + ",最后接收消息的时间（毫秒）:" + (lastRecieveTime == null ? "空" : lastRecieveTime);
	}

}