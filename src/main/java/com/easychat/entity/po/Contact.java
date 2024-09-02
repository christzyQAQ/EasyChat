package com.easychat.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.easychat.enums.DateTimePatternEnum;
import com.easychat.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 
 * @auther: 系统
 * @date: 2024-07-21 16:13
 */
public class Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6499483048468920975L;

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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 状态：0 非好友 1 好友 2 已删除好友 3 被好友删除 4 已拉黑好友 5 被好友拉黑 
	 */
	@JsonIgnore
	private Integer status;

	/**
	 * 最后更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;
	
	private String contactName ;
	
	private Integer sex;
	


	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
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

	@Override
	public String toString() {
		return "用户ID:" + (userId == null ? "空" : userId) + ",联系人ID或群组ID:" + (contactId == null ? "空" : contactId) + ",联系人类型：0：好友1：群组:" + (contactType == null ? "空" : contactType) + ",创建时间:" + (createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ",状态：0 非好友 1 好友 2 已删除好友 3 被好友删除 4 已拉黑好友 5 被好友拉黑 :" + (status == null ? "空" : status) + ",最后更新时间:" + (lastUpdateTime == null ? "空" : DateUtil.format(lastUpdateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}

}