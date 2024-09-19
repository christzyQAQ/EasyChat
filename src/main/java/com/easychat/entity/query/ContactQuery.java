package com.easychat.entity.query;

import java.util.Date;
/**
 * 查询对象
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
public class ContactQuery extends BaseQuery {

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 联系人ID或群组ID
	 */
	private String contactId;

	/**
	 * 联系人类型：0：好友1：群组
	 */
	private Integer contactType;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 状态：0 非好友 1 好友 2 已删除好友 3 被好友删除 4 已拉黑好友 5 被好友拉黑 
	 */
	private Integer status;

	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;


	private String userIdFuzzy;

	private String contactIdFuzzy;

	private String createTimeStart;

	private String createTimeEnd;

	private String lastUpdateTimeStart;

	private String lastUpdateTimeEnd;
	
	private boolean queryUserInfo;
	
	private boolean queryGroupInfo;
	
	private boolean queryContactUserInfo;
	
	private boolean ExcludeMyGroup;
	
	private Integer[] statusArray;
	
	
	public Integer[] getStatusArray() {
		return statusArray;
	}
	public void setStatusArray(Integer[] statusArray) {
		this.statusArray = statusArray;
	}
	public boolean isExcludeMyGroup() {
		return ExcludeMyGroup;
	}
	public void setExcludeMyGroup(boolean queryExcludeMyGroup) {
		this.ExcludeMyGroup = queryExcludeMyGroup;
	}
	public boolean isQueryContactUserInfo() {
		return queryContactUserInfo;
	}
	public void setQueryContactUserInfo(boolean queryContactUserInfo) {
		this.queryContactUserInfo = queryContactUserInfo;
	}
	public boolean isQueryGroupInfo() {
		return queryGroupInfo;
	}
	public void setQueryGroupInfo(boolean queryGroupInfo) {
		this.queryGroupInfo = queryGroupInfo;
	}

	

	public boolean isQueryUserInfo() {
		return queryUserInfo;
	}
	public void setQueryUserInfo(boolean queryUserInfo) {
		this.queryUserInfo = queryUserInfo;
	}
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

	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}
	public Integer getContactType() {
		return contactType;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return createTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return status;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
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

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setLastUpdateTimeStart(String lastUpdateTimeStart) {
		this.lastUpdateTimeStart = lastUpdateTimeStart;
	}
	public String getLastUpdateTimeStart() {
		return lastUpdateTimeStart;
	}

	public void setLastUpdateTimeEnd(String lastUpdateTimeEnd) {
		this.lastUpdateTimeEnd = lastUpdateTimeEnd;
	}
	public String getLastUpdateTimeEnd() {
		return lastUpdateTimeEnd;
	}


}