package com.easychat.enums;

import com.easychat.utils.StringTools;

import ch.qos.logback.core.joran.util.StringToObjectConverter;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 17, 202411:02:59 PM
* @ClassName:UserContactTypeEnum.java

*/
public enum  UserContactTypeEnum {

	USER (0,"U","好友"),
	GROUP (1,"G","群组");
	private Integer type;
	private String prefix;
	private String desc;
	
	UserContactTypeEnum(Integer type,String prefix,String desc){
		this.type=type;
		this.prefix=prefix;
		this.desc=desc;		
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	
	public static UserContactTypeEnum getByName(String name) {		
		try {
			if(StringTools.isEmpty(name)) {
				return null;
			}
			return UserContactTypeEnum.valueOf(name.toUpperCase());
		} catch (Exception e) {
			return null;
		}
		
	}	
	public static UserContactTypeEnum getByprefix(String prefix) {		
		try {
			if(StringTools.isEmpty(prefix)||prefix.trim().length()==0) {
				return null;
			}
			prefix=prefix.substring(0,1);
			for(UserContactTypeEnum typeEnum:UserContactTypeEnum.values()) {
				if(typeEnum.getPrefix().equals(prefix)) {
					return typeEnum;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
		
	}
	

	
}
