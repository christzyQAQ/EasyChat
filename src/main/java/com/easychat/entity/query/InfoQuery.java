package com.easychat.entity.query;

import java.util.Date;
/**
 * 用户信息查询对象
 * @auther: 系统
 * @date: 2024-07-14 22:48
 */
public class InfoQuery extends BaseQuery {

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 0:好友申请直接加入 1:好友申请需同意后加入
	 */
	private Integer joinType;

	/**
	 * 性别 0:男 1:女
	 */
	private Integer sex;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 个性签名
	 */
	private String personalSignature;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 账号创建时间
	 */
	private Date createTime;

	/**
	 * 最后一次登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 地区
	 */
	private String areaName;

	/**
	 * 地区编号
	 */
	private String areaCode;

	/**
	 * 最后离线时间
	 */
	private Date lastOffTime;


	private String userIdFuzzy;

	private String emailFuzzy;

	private String nickNameFuzzy;

	private String passwordFuzzy;

	private String personalSignatureFuzzy;

	private String createTimeStart;

	private String createTimeEnd;

	private String lastLoginTimeStart;

	private String lastLoginTimeEnd;

	private String areaNameFuzzy;

	private String areaCodeFuzzy;

	private String lastOffTimeStart;

	private String lastOffTimeEnd;


	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getNickName() {
		return nickName;
	}

	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}
	public Integer getJoinType() {
		return joinType;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getSex() {
		return sex;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}

	public void setPersonalSignature(String personalSignature) {
		this.personalSignature = personalSignature;
	}
	public String getPersonalSignature() {
		return personalSignature;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return status;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return createTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaName() {
		return areaName;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaCode() {
		return areaCode;
	}

	public void setLastOffTime(Date lastOffTime) {
		this.lastOffTime = lastOffTime;
	}
	public Date getLastOffTime() {
		return lastOffTime;
	}


	public void setUserIdFuzzy(String userIdFuzzy) {
		this.userIdFuzzy = userIdFuzzy;
	}
	public String getUserIdFuzzy() {
		return userIdFuzzy;
	}

	public void setEmailFuzzy(String emailFuzzy) {
		this.emailFuzzy = emailFuzzy;
	}
	public String getEmailFuzzy() {
		return emailFuzzy;
	}

	public void setNickNameFuzzy(String nickNameFuzzy) {
		this.nickNameFuzzy = nickNameFuzzy;
	}
	public String getNickNameFuzzy() {
		return nickNameFuzzy;
	}

	public void setPasswordFuzzy(String passwordFuzzy) {
		this.passwordFuzzy = passwordFuzzy;
	}
	public String getPasswordFuzzy() {
		return passwordFuzzy;
	}

	public void setPersonalSignatureFuzzy(String personalSignatureFuzzy) {
		this.personalSignatureFuzzy = personalSignatureFuzzy;
	}
	public String getPersonalSignatureFuzzy() {
		return personalSignatureFuzzy;
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

	public void setLastLoginTimeStart(String lastLoginTimeStart) {
		this.lastLoginTimeStart = lastLoginTimeStart;
	}
	public String getLastLoginTimeStart() {
		return lastLoginTimeStart;
	}

	public void setLastLoginTimeEnd(String lastLoginTimeEnd) {
		this.lastLoginTimeEnd = lastLoginTimeEnd;
	}
	public String getLastLoginTimeEnd() {
		return lastLoginTimeEnd;
	}

	public void setAreaNameFuzzy(String areaNameFuzzy) {
		this.areaNameFuzzy = areaNameFuzzy;
	}
	public String getAreaNameFuzzy() {
		return areaNameFuzzy;
	}

	public void setAreaCodeFuzzy(String areaCodeFuzzy) {
		this.areaCodeFuzzy = areaCodeFuzzy;
	}
	public String getAreaCodeFuzzy() {
		return areaCodeFuzzy;
	}

	public void setLastOffTimeStart(String lastOffTimeStart) {
		this.lastOffTimeStart = lastOffTimeStart;
	}
	public String getLastOffTimeStart() {
		return lastOffTimeStart;
	}

	public void setLastOffTimeEnd(String lastOffTimeEnd) {
		this.lastOffTimeEnd = lastOffTimeEnd;
	}
	public String getLastOffTimeEnd() {
		return lastOffTimeEnd;
	}


}