package com.easychat.dto;

import java.io.Serializable;

import com.easychat.entity.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 21, 20242:12:53 PM
* @ClassName:SysSettingDto.java

*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysSettingDto implements Serializable {


	private static final long serialVersionUID = 1184322884467016947L;
	
	//设置最大群组数
	private Integer maxGroupCount=5;
	//群组最大人数
	private Integer maxGroupMemberCount=500;
	//图片最大尺寸
	private Integer maxImageSize=500;
	//视频最大尺寸
	private Integer maxVideoSize=500;
	//文件大小
	private Integer maxFileSize=500;
	//机器人ID
	private String robotUid=Constants.ROBOT_UID;
	//机器人名字
	private String robotNickName="EasyChat亲";
	//欢迎语
	private String robotWelcome="宝，你好呀！欢迎使用EasyChat";
	
	
	public Integer avatarFile() {
		return maxGroupCount;
	}
	public Integer getMaxGroupCount() {
		return maxGroupCount;
	}
	public void setMaxGroupCount(Integer maxGroupCount) {
		this.maxGroupCount = maxGroupCount;
	}
	public Integer getMaxGroupMemberCount() {
		return maxGroupMemberCount;
	}
	public void setMaxGroupMemberCount(Integer maxGroupMemberCount) {
		this.maxGroupMemberCount = maxGroupMemberCount;
	}
	public Integer getMaxImageSize() {
		return maxImageSize;
	}
	public void setMaxImageSize(Integer maxImageSize) {
		this.maxImageSize = maxImageSize;
	}
	public Integer getMaxVideoSize() {
		return maxVideoSize;
	}
	public void setMaxVideoSize(Integer maxVideoSize) {
		this.maxVideoSize = maxVideoSize;
	}
	public Integer getMaxFileSize() {
		return maxFileSize;
	}
	public void setMaxFileSize(Integer maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
	public String getRobotUid() {
		return robotUid;
	}
	public void setRobotUid(String robotUid) {
		this.robotUid = robotUid;
	}
	public String getRobotNickName() {
		return robotNickName;
	}
	public void setRobotNickName(String robotNickName) {
		this.robotNickName = robotNickName;
	}
	public String getRobotWelcome() {
		return robotWelcome;
	}
	public void setRobotWelcome(String robotWelcome) {
		this.robotWelcome = robotWelcome;
	}
}
