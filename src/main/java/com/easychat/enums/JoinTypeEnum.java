package com.easychat.enums;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 20, 202410:57:41 PM
* @ClassName:JoinTypeEnum.java

*/
import com.easychat.utils.StringTools;
public enum JoinTypeEnum {

	JOIN (0,"直接加入"),
	APPLY (1,"需要审核");
	
	private Integer type;
	private String desc;
	
	JoinTypeEnum (Integer type, String desc){
		this.type=type;
		this.desc=desc;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	
	public static JoinTypeEnum getbyName(String name) {
		try {
			if(StringTools.isEmpty(name)) {
				return null;
			}
			return JoinTypeEnum.valueOf(name.toUpperCase());
		} catch ( Exception e) {
			
		}
		return null;
		
	}
	
}
