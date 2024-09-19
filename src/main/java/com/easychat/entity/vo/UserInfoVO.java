package com.easychat.entity.vo;

import java.io.Serializable;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 20, 20242:43:29 PM
* @ClassName:UserInfoVO.java

*/
public class UserInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2308874847403796928L;
	
	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 性别 男0 女1
	 */
	private Integer sex;
	/**
	 * 0:好友申请直接加入 1:好友申请需同意后加入
	 */
	private Integer joinType;

	/**
	 * 个性签名
	 */
	private String personalSignature;
	
	/**
	 * 地区代码
	 */
	private String areaCode;
	/**
	 * 地区名
	 */
	private String areaName;
	/**
	 * token
	 */
	private String token;
	/**
	 * 是否为管理员
	 */
	private boolean admin;
	/**
	 * 
	 */
	private Integer contractStatus;
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * @return the joinType
	 */
	public Integer getJoinType() {
		return joinType;
	}
	/**
	 * @param joinType the joinType to set
	 */
	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}
	/**
	 * @return the personalSignature
	 */
	public String getPersonalSignature() {
		return personalSignature;
	}
	/**
	 * @param personalSignature the personalSignature to set
	 */
	public void setPersonalSignature(String personalSignature) {
		this.personalSignature = personalSignature;
	}
	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}
	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}
	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	/**
	 * @return the contractStatus
	 */
	public Integer getContractStatus() {
		return contractStatus;
	}
	/**
	 * @param contractStatus the contractStatus to set
	 */
	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}
	
}
