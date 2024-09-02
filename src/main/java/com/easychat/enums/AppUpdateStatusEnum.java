package com.easychat.enums;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 26, 202410:17:17 PM
* @ClassName:AppUpdateFileTypeEnum.enum

*/
public enum AppUpdateStatusEnum {

	INIT(0,"未发布"),
	GRAYSCALE(1,"灰度发布"),
	ALL(2,"全面发布");
	
	private Integer status;
	private String desc;
	
	AppUpdateStatusEnum (Integer status,String desc){
		this.status=status;
		this.desc=desc;
	}
	 
	public static AppUpdateStatusEnum getByStatus(Integer status) {
		for (AppUpdateStatusEnum at : AppUpdateStatusEnum.values()) {
			if(at.status.equals(status)) {
				return at;
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
