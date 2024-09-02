package com.easychat.enums;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 18, 202412:03:25 AM
* @ClassName:BeautyAccountStatusEnum.java

*/

import java.security.KeyStore.PrivateKeyEntry;

public enum BeautyAccountStatusEnum {

	NO_USE(0,"未使用"),
	USEED(1,"已使用");
	 private Integer status;
	 private String  desc;
	
	 BeautyAccountStatusEnum(Integer status,String desc){
		 this.status=status;
		 this.desc=desc;
	 }
	 public BeautyAccountStatusEnum getbyStatus(Integer status) {
		 for(BeautyAccountStatusEnum item:BeautyAccountStatusEnum .values())
		 {
			 if(item.getStatus().equals(status)){
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
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	 
}
