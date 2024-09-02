package com.easychat.dto;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 28, 20247:45:51 PM
* @ClassName:WsInitDate.java

*/

import java.util.List;

import com.easychat.entity.po.Message;
import com.easychat.entity.po.SessionUser;

public class WsInitDate {
	

	private List<SessionUser> sessionUserList;
	
	private List<Message> messagesList;
	//申请通知条数
	private Integer applyCount;
	
	
	
	public List<SessionUser> getSessionUserList() {
		return sessionUserList;
	}
	public void setSessionUserList(List<SessionUser> sessionUserList) {
		this.sessionUserList = sessionUserList;
	}
	public List<Message> getMessagesList() {
		return messagesList;
	}
	public void setMessagesList(List<Message> messagesList) {
		this.messagesList = messagesList;
	}
	public Integer getApplyCount() {
		return applyCount;
	}
	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
	}
	
	
	
}
