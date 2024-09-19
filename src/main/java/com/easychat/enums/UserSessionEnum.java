package com.easychat.enums;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Sep 19, 202410:35:20 AM
* @ClassName:UserSessionEnum.java

*/
public enum UserSessionEnum {
	PIN(1,"置顶"),
	UNPIN(0,"取消置顶");
	private int status;
	private String desc;
	
	private UserSessionEnum(int status,String desc) {
		this.status=status;
		this.desc=desc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

}
