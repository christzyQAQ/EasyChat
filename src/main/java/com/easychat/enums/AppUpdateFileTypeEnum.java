package com.easychat.enums;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 26, 202410:17:17 PM
* @ClassName:AppUpdateFileTypeEnum.enum

*/
public enum AppUpdateFileTypeEnum {

	LOCAL(0,"本地"),
	OUTLINK(1,"外链");
	
	private Integer type;
	private String desc;
	
	AppUpdateFileTypeEnum (Integer type,String desc){
		this.type=type;
		this.desc=desc;
	}
	 
	public static AppUpdateFileTypeEnum getBytype(Integer type) {
		for (AppUpdateFileTypeEnum at : AppUpdateFileTypeEnum.values()) {
			if(at.type.equals(type)) {
				return at;
			}			
		}
		return null;
	}

	public Integer getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}
}
