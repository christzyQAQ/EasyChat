package com.easychat.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.easychat.enums.DateTimePatternEnum;
import com.easychat.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 
 * @auther: 系统
 * @date: 2024-07-21 16:13
 */
public class ContactApply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4283117560746482118L;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastApplyTime;

	/**
	 * 状态：0:待处理 1： 已同意 2：已拒绝 3：已拉黑
	 */
	@JsonIgnore
	private Integer status;

	/**
	 * 申请信息
	 */
	private String applyInfo;
	
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	private String contactName;


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
	public void setLastApplyTime(Date curTime) {
		this.lastApplyTime = curTime;
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

	@Override
	public String toString() {
		return "自增id:" + (applyId == null ? "空" : applyId) + ",申请人id:" + (applyUserId == null ? "空" : applyUserId) + ",接收人id:" + (receiveUserId == null ? "空" : receiveUserId) + ",联系人类型：0：好友 1：群组:" + (contactType == null ? "空" : contactType) + ",联系人群组id:" + (contactId == null ? "空" : contactId) + ",最后申请时间:" + (lastApplyTime == null ? "空" : DateUtil.format(lastApplyTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ",状态：0:待处理 1： 已同意 2：已拒绝 3：已拉黑:" + (status == null ? "空" : status) + ",申请信息:" + (applyInfo == null ? "空" : applyInfo);
	}

}