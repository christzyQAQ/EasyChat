package com.easychat.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.format.annotation.DateTimeFormat;
import com.easychat.enums.DateTimePatternEnum;
import com.easychat.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
public class GroupInfo implements Serializable {


	/**
	 * 群id
	 */
	private String groupId;

	/**
	 * 群名称
	 */
	private String groupName;

	/**
	 * 群主名
	 */
	private String groupOwnerId;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 群公告
	 */
	private String groupNotice;

	/**
	 * 0:直接加入；1管理员同意后加入
	 */
	private Integer joinType;

	/**
	 * 状态：1：正常 0：解散
	 */
	@JsonIgnore
	private Integer status;
	
	/**
	 * 群成员数量
	 */
	private Integer memberCount;
	
	public String getGroupOwner() {
		return groupOwner;
	}
	public void setGroupOwner(String groupOwner) {
		this.groupOwner = groupOwner;
	}
	private String groupOwner;
	
	


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupOwnerId(String groupOwnerId) {
		this.groupOwnerId = groupOwnerId;
	}
	public String getGroupOwnerId() {
		return groupOwnerId;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setGroupNotice(String groupNotice) {
		this.groupNotice = groupNotice;
	}
	public String getGroupNotice() {
		return groupNotice;
	}
	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}
	public Integer getJoinType() {
		return joinType;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "群id:" + (groupId == null ? "空" : groupId) + ",群名称:" + (groupName == null ? "空" : groupName) + ",群主名:" + (groupOwnerId == null ? "空" : groupOwnerId) + ",创建时间:" + (createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ",群公告:" + (groupNotice == null ? "空" : groupNotice) + ",0:直接加入；1管理员同意后加入:" + (joinType == null ? "空" : joinType) + ",状态：1：正常 0：解散:" + (status == null ? "空" : status);
	}

	
	public Integer getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

}