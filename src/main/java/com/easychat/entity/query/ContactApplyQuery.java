package com.easychat.entity.query;

import java.util.Date;
/**
 * 查询对象
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
public class ContactApplyQuery extends BaseQuery {

	/**
	 * 自增id
	 */
	private Integer applyId;

	/**
	 * 申请人id
	 */
	private String applyUserId;

	/**
	 * 接收人id
	 */
	private String receiveUserId;

	/**
	 * 联系人类型：0：好友 1：群组
	 */
	private Integer contactType;

	/**
	 * 联系人群组id
	 */
	private String contactId;

	/**
	 * 最后申请时间
	 */
	private Date lastApplyTime;

	/**
	 * 状态：0:待处理 1： 已同意 2：已拒绝 3：已拉黑
	 */
	private Integer status;

	/**
	 * 申请信息
	 */
	private String applyInfo;


	private String applyUserIdFuzzy;

	private String receiveUserIdFuzzy;

	private String contactIdFuzzy;

	private String lastApplyTimeStart;

	private String lastApplyTimeEnd;

	private String applyInfoFuzzy;
	
	private boolean queryContactInfo;
	
	private Long lastApplyTimestamp;
	
	
	


	public boolean isQueryContactInfo() {
		return queryContactInfo;
	}
	public void setQueryContactInfo(boolean queryContactInfo) {
		this.queryContactInfo = queryContactInfo;
	}
	public Long getLastApplyTimestamp() {
		return lastApplyTimestamp;
	}
	public void setLastApplyTimestamp(Long lastApplyTimestamp) {
		this.lastApplyTimestamp = lastApplyTimestamp;
	}
	public boolean isGetQueryContactInfo() {
		return queryContactInfo;
	}
	public void setGetQueryContactInfo(boolean getQueryContactInfo) {
		this.queryContactInfo = getQueryContactInfo;
	}


	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}
	public Integer getApplyId() {
		return applyId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	public String getApplyUserId() {
		return applyUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setContactType(Integer contactType) {
		this.contactType = contactType;
	}
	public Integer getContactType() {
		return contactType;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactId() {
		return contactId;
	}

	public void setLastApplyTime(Date lastApplyTime) {
		this.lastApplyTime = lastApplyTime;
	}
	public Date getLastApplyTime() {
		return lastApplyTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return status;
	}

	public void setApplyInfo(String applyInfo) {
		this.applyInfo = applyInfo;
	}
	public String getApplyInfo() {
		return applyInfo;
	}


	public void setApplyUserIdFuzzy(String applyUserIdFuzzy) {
		this.applyUserIdFuzzy = applyUserIdFuzzy;
	}
	public String getApplyUserIdFuzzy() {
		return applyUserIdFuzzy;
	}

	public void setReceiveUserIdFuzzy(String receiveUserIdFuzzy) {
		this.receiveUserIdFuzzy = receiveUserIdFuzzy;
	}
	public String getReceiveUserIdFuzzy() {
		return receiveUserIdFuzzy;
	}

	public void setContactIdFuzzy(String contactIdFuzzy) {
		this.contactIdFuzzy = contactIdFuzzy;
	}
	public String getContactIdFuzzy() {
		return contactIdFuzzy;
	}

	public void setLastApplyTimeStart(String lastApplyTimeStart) {
		this.lastApplyTimeStart = lastApplyTimeStart;
	}
	public String getLastApplyTimeStart() {
		return lastApplyTimeStart;
	}

	public void setLastApplyTimeEnd(String lastApplyTimeEnd) {
		this.lastApplyTimeEnd = lastApplyTimeEnd;
	}
	public String getLastApplyTimeEnd() {
		return lastApplyTimeEnd;
	}

	public void setApplyInfoFuzzy(String applyInfoFuzzy) {
		this.applyInfoFuzzy = applyInfoFuzzy;
	}
	public String getApplyInfoFuzzy() {
		return applyInfoFuzzy;
	}


}