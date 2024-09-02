package com.easychat.enums;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 18, 202411:09:11 AM
* @ClassName:UserStatus.java

*/

public enum UserStatusEnum {

	DISABLE(0,"禁用"),
	ENABLE(1,"启用");
	 
	private Integer status;
	private String  desc;
	
	   UserStatusEnum(Integer status, String desc){
		this.status=status;
		this.desc=desc;
	}
	
	
	public static UserStatusEnum getByStatus(Integer Status) {
		
		for(UserStatusEnum item:UserStatusEnum.values()) {
			if(item.getStatus().equals(Status)) {
				return item;
			}
			
		}
		return null;
		
	}


	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}


	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	
	
	
	
}
