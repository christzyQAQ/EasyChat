package com.easychat.enums;

import com.easychat.utils.StringTools;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 23, 20244:22:12 PM
* @ClassName:UserContactApplyStatusEnum.java

*/
public enum UserContactApplyStatusEnum {

	INIT(0,"待处理"),
	PASS(1,"已同意"),
	REJECT(2,"已拒绝"),
	BLACKLIST(3,"已拉黑");
	
	private Integer status;
	private  String desc;
	
	public Integer getStatus() {
		return status;
	}

	public String getDesc() {
		return desc;
	}

	UserContactApplyStatusEnum(Integer status, String desc){
		this.status=status;
		this.desc=desc;
	}

	public static UserContactApplyStatusEnum getByStatus(String status) {
		try {
			if(StringTools.isEmpty(status)) {
				return null;
			}
			return UserContactApplyStatusEnum.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
				
	}
	public static UserContactApplyStatusEnum getByStatus(Integer status) {
		
		for(UserContactApplyStatusEnum item:UserContactApplyStatusEnum.values()) {
			if(item.getStatus().equals(status)) {
				return item;
			}
		}
		return null;
		}
				
	}
	

	
	


