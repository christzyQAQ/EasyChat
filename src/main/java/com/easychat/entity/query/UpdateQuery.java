package com.easychat.entity.query;

import java.util.Date;
/**
 * app发布查询对象
 * @auther: 系统
 * @date: 2024-07-26 21:37
 */
public class UpdateQuery extends BaseQuery {

	/**
	 * 自增ID
	 */
	private Integer id;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 更新描述
	 */
	private String updateDesc;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 0：未发布1：灰度发布2：全网发布
	 */
	private Integer status;

	/**
	 * 灰度uid
	 */
	private Integer grayscaleUid;

	/**
	 * 文件类型：0：本地1：外链
	 */
	private Integer fileType;

	/**
	 * 外链地址
	 */
	private String outerLink;


	private String versionFuzzy;

	private String updateDescFuzzy;

	private String createTimeStart;

	private String createTimeEnd;

	private String outerLinkFuzzy;


	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersion() {
		return version;
	}

	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}
	public String getUpdateDesc() {
		return updateDesc;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTime() {
		return createTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatus() {
		return status;
	}

	public void setGrayscaleUid(Integer grayscaleUid) {
		this.grayscaleUid = grayscaleUid;
	}
	public Integer getGrayscaleUid() {
		return grayscaleUid;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	public Integer getFileType() {
		return fileType;
	}

	public void setOuterLink(String outerLink) {
		this.outerLink = outerLink;
	}
	public String getOuterLink() {
		return outerLink;
	}


	public void setVersionFuzzy(String versionFuzzy) {
		this.versionFuzzy = versionFuzzy;
	}
	public String getVersionFuzzy() {
		return versionFuzzy;
	}

	public void setUpdateDescFuzzy(String updateDescFuzzy) {
		this.updateDescFuzzy = updateDescFuzzy;
	}
	public String getUpdateDescFuzzy() {
		return updateDescFuzzy;
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

	public void setOuterLinkFuzzy(String outerLinkFuzzy) {
		this.outerLinkFuzzy = outerLinkFuzzy;
	}
	public String getOuterLinkFuzzy() {
		return outerLinkFuzzy;
	}


}