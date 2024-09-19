package com.easychat.entity.query;

/**
 * 会话用户查询对象
 * @auther: 系统
 * @date: 2024-07-28 17:23
 */
public class SessionUserQuery extends BaseQuery {

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


	private String userIdFuzzy;

	private String contactIdFuzzy;

	private String sessionIdFuzzy;

	private String contactNameFuzzy;


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


	public void setUserIdFuzzy(String userIdFuzzy) {
		this.userIdFuzzy = userIdFuzzy;
	}
	public String getUserIdFuzzy() {
		return userIdFuzzy;
	}

	public void setContactIdFuzzy(String contactIdFuzzy) {
		this.contactIdFuzzy = contactIdFuzzy;
	}
	public String getContactIdFuzzy() {
		return contactIdFuzzy;
	}

	public void setSessionIdFuzzy(String sessionIdFuzzy) {
		this.sessionIdFuzzy = sessionIdFuzzy;
	}
	public String getSessionIdFuzzy() {
		return sessionIdFuzzy;
	}

	public void setContactNameFuzzy(String contactNameFuzzy) {
		this.contactNameFuzzy = contactNameFuzzy;
	}
	public String getContactNameFuzzy() {
		return contactNameFuzzy;
	}


}