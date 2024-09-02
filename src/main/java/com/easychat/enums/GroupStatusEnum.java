package com.easychat.enums;

import ch.qos.logback.core.status.Status;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 22, 20244:28:20 PM
* @ClassName:GroupStatusEnum.java

*/
public enum GroupStatusEnum {

	NORMAL (0,"正常"),
	DISSOLUTION (1,"解散");

	private  Integer status;
	private  String desc;

	
		GroupStatusEnum (Integer status, String desc) {
		this.status = status;
		this.desc = desc;
	}
		
		private  GroupStatusEnum getByStatus() {
			for(GroupStatusEnum item:GroupStatusEnum.values()) {
				if(item.getStatus().equals(status)) {
					return item;
					}				
			}
			return null;
		}

		public Integer getStatus() {
			return status;
		}

		public String getDesc() {
			return desc;
		}



}
