package com.easychat.enums;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 29, 202410:08:03 AM
* @ClassName:MessageType.java

*/
public enum MessageTypeEnum {

	INIT(0,"","连接ws信息"),
	ADD_FRIEDN(1,"","添加好友打招呼消息"),
	CHAT(2,"","普通连接消息"),
	GROUP_CREAT(3,"群组已经创建好，可以和好友一起愉快的聊天了","群创建成功"),
	CONTACT_APPLY(4,"","好友申请"),
	MEDIA_CHAT(5,"","媒体文件"),
	FILE_UPLOAD(6,"","文件上传完成"),
	FORCE_OFF_LINE(7,"","强制下线"),
	DISSOLUSION_GROUP(8,"群聊已解散","解散群聊"),
	ADD_GROUP(9,"%s加入了群组","加入群聊"),
	CONTACT_NAME_UPDATE(10,"","更新昵称"),
	LEAVE_GROUP(11,"%s退出了群聊","退出群聊"),
	REMOVE_GROUP(12,"%s被管理员移出了群聊","被管理员移出了群聊"),
	ADD_FRIEDN_SELF(13,"","添加好友打招呼消息");
	

	private Integer type;
	private String initMessage;
	private String desc;
	
	MessageTypeEnum (Integer type,String initMessage,String desc){
		this.type=type;
		this.initMessage=initMessage;
		this.desc=desc;
	}

	public static MessageTypeEnum getByType(Integer type) {
		for(MessageTypeEnum item:MessageTypeEnum.values()) {
			if (item.getType().equals(type)) {
				return item;
			}
		}
		return null;
	}
	
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getInitMessage() {
		return initMessage;
	}

	public void setInitMessage(String initMessage) {
		this.initMessage = initMessage;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
