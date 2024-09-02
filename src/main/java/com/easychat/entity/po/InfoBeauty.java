package com.easychat.entity.po;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 
 * @auther: 系统
 * @date: 2024-07-14 22:48
 */
public class InfoBeauty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4229474718501250039L;

	/**
	 * 自增ID
	 */
	private Integer id;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 状态
	 */
	@JsonIgnore
	private Integer status;


	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "自增ID:" + (id == null ? "空" : id) + ",邮箱:" + (email == null ? "空" : email) + ",用户ID:" + (userId == null ? "空" : userId) + ",状态:" + (status == null ? "空" : status);
	}

}