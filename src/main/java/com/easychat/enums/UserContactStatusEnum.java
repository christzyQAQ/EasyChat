package com.easychat.enums;

import com.easychat.utils.StringTools;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 21, 20249:47:21 PM
* @ClassName:UserContactStatusEnum.java

*/
public enum UserContactStatusEnum {

	NOT_FRIEND(0,"非好友"),
	FRIEND(1,"好友"),
	DEL(2,"删除好友"),
	DEL_BE(3,"被好友删除"),
	BLACKLIST(4,"已将其拉入黑名单"),
	BLACKLIST_BE(5,"被对方拉进黑名单"),
	FIRST_BLACKLIST_BE(6,"首次加入便被拉入黑名单");
	
	private Integer status;
	private String  desc;
	 
	UserContactStatusEnum(Integer status,String desc){
		this.status=status;
		this.desc=desc;
		
	}
	
	public static UserContactStatusEnum getByStatus(String status) {
	
		try {
			if(StringTools.isEmpty(status)) {
				return null;
			}
			return	 UserContactStatusEnum.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
		
	}
	
	
	
	
	public static UserContactStatusEnum getByStatus(Integer status) {
		for(UserContactStatusEnum item:UserContactStatusEnum.values()) {
			if(item.getStatus().equals(status))
				return item;
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
	
	
	
	

