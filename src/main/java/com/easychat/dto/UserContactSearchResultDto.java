package com.easychat.dto;

import com.easychat.enums.UserContactStatusEnum;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 22, 202411:16:12 PM
* @ClassName:UserContactSearchResult.java

*/
public class UserContactSearchResultDto {

	private String contactId;
	private String contactType;
	private String NickName;
	private Long avatarLastUpdate;
	private Integer status;
	private String statusName;
	private Integer sex;
	private String areaName;
	
	
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public Long getAvatarLastUpdate() {
		return avatarLastUpdate;
	}
	public void setAvatarLastUpdate(Long avatarLastUpdate) {
		this.avatarLastUpdate = avatarLastUpdate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusName() {
		UserContactStatusEnum statusEume= UserContactStatusEnum.getByStatus(status);
		return statusEume == null? null:statusEume.getDesc();
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


}
