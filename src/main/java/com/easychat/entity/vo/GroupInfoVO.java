package com.easychat.entity.vo;

import java.util.List;

import com.easychat.entity.po.Contact;
import com.easychat.entity.po.GroupInfo;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 22, 20249:04:24 PM
* @ClassName:GroupInfoVO.java

*/
public class GroupInfoVO {
	private GroupInfo groupInfo;
	public GroupInfo getGroupInfo() {
		return groupInfo;
	}
	public void setGroupInfo(GroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}
	public List<Contact> getContactList() {
		return contactList;
	}
	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}
	private  List <Contact> contactList;
	

}
