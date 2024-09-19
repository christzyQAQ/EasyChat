package com.easychat.entity.query;

/**
 * 查询对象
 * @auther: christzy
 * @date: 2024-09-19 10:18
 */
public class SessionQuery extends BaseQuery {

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


	private String sessionIdFuzzy;

	private String lastMessageFuzzy;


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


	public void setSessionIdFuzzy(String sessionIdFuzzy) {
		this.sessionIdFuzzy = sessionIdFuzzy;
	}
	public String getSessionIdFuzzy() {
		return sessionIdFuzzy;
	}

	public void setLastMessageFuzzy(String lastMessageFuzzy) {
		this.lastMessageFuzzy = lastMessageFuzzy;
	}
	public String getLastMessageFuzzy() {
		return lastMessageFuzzy;
	}


}