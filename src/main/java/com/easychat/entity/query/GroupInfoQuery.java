package com.easychat.entity.query;

import java.util.Date;
/**
 * 查询对象
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
public class GroupInfoQuery extends BaseQuery {

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
	private Integer status;


	private String groupIdFuzzy;

	private String groupNameFuzzy;

	private String groupOwnerIdFuzzy;

	private String createTimeStart;

	private String createTimeEnd;

	private String groupNoticeFuzzy;
	
	private boolean queryGroupOwner;
	
	private boolean queryGroupMember;



	public boolean isQueryGroupMember() {
		return queryGroupMember;
	}
	public void setQueryGroupMember(boolean queryGroupMember) {
		this.queryGroupMember = queryGroupMember;
	}
	public boolean isQueryGroupOwner() {
		return queryGroupOwner;
	}
	public void setQueryGroupOwner(boolean queryGroupOwner) {
		this.queryGroupOwner = queryGroupOwner;
	}
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


	public void setGroupIdFuzzy(String groupIdFuzzy) {
		this.groupIdFuzzy = groupIdFuzzy;
	}
	public String getGroupIdFuzzy() {
		return groupIdFuzzy;
	}

	public void setGroupNameFuzzy(String groupNameFuzzy) {
		this.groupNameFuzzy = groupNameFuzzy;
	}
	public String getGroupNameFuzzy() {
		return groupNameFuzzy;
	}

	public void setGroupOwnerIdFuzzy(String groupOwnerIdFuzzy) {
		this.groupOwnerIdFuzzy = groupOwnerIdFuzzy;
	}
	public String getGroupOwnerIdFuzzy() {
		return groupOwnerIdFuzzy;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setGroupNoticeFuzzy(String groupNoticeFuzzy) {
		this.groupNoticeFuzzy = groupNoticeFuzzy;
	}
	public String getGroupNoticeFuzzy() {
		return groupNoticeFuzzy;
	}


}