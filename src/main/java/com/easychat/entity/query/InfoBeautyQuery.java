package com.easychat.entity.query;

/**
 * 查询对象
 * @auther: 系统
 * @date: 2024-07-14 22:48
 */
public class InfoBeautyQuery extends BaseQuery {

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
	private Integer status;


	private String emailFuzzy;

	private String userIdFuzzy;


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


	public void setEmailFuzzy(String emailFuzzy) {
		this.emailFuzzy = emailFuzzy;
	}
	public String getEmailFuzzy() {
		return emailFuzzy;
	}

	public void setUserIdFuzzy(String userIdFuzzy) {
		this.userIdFuzzy = userIdFuzzy;
	}
	public String getUserIdFuzzy() {
		return userIdFuzzy;
	}


}