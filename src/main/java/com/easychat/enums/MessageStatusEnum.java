package com.easychat.enums;

import java.util.Iterator;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 29, 20242:51:30 PM
* @ClassName:MessageStatusEnum.java

*/
public enum MessageStatusEnum {

	SENDING(0,"发送中"),
	SENDED(1,"已发送");
	
	private Integer status;
	private String desc;
	
	MessageStatusEnum(Integer status, String desc){
		this.status=status;
		this.desc=desc;		
	}

	public static MessageStatusEnum getByStatus(Integer status) {
		for(MessageStatusEnum item: MessageStatusEnum.values()) {
			if (item.getStatus().equals(status)) {
				return item;
			}			
		}
		return null;
	}
	
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
