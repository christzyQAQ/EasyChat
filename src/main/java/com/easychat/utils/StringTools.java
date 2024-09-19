package com.easychat.utils;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.maven.artifact.versioning.ComparableVersion;

import com.easychat.entity.constants.Constants;
import com.easychat.enums.UserContactTypeEnum;

import org.apache.commons.lang3.StringUtils;


/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 17, 202410:48:57 PM
* @ClassName:StringTools.java

*/
public class StringTools {

	/**
	 * 随机生成用户的id，根据前缀判断是用户或群组
	 * 
	 * @return
	 */
	public static String getUserId() {
		return UserContactTypeEnum.USER.getPrefix() + getRandomNumber(Constants.LENGTH_11);

	}

	public static String getGroupId() {
		return UserContactTypeEnum.GROUP.getPrefix() + getRandomNumber(Constants.LENGTH_11);

	}
	
	
	public static String getChatSessionId4User(String[]  userIds) 
	{
		Arrays.sort(userIds);
		return encodeMd5(StringUtils.join(userIds,""));		
	}
	
	public static String getChatSessionId4Group(String GroupId) 
	{
		return encodeMd5(GroupId);		
	}
	

	//
	/**
	 * 获得一个不包含字母的指定长度的随机字符串
	 * 
	 * @param count 字符串长度
	 * @return
	 */
	public static final String getRandomNumber(Integer count) {

		return RandomStringUtils.random(count, false, true);

	}

	/**
	 * 获得一个指定长度的随机字符串,可以是字母、数字，或者两者的组合
	 * 
	 * @param count
	 * @return
	 */
	public static final String getRandomString(Integer count) {

		return RandomStringUtils.random(count, true, true);

	}

	// 判断传入字符串是否为空
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str) || "null".equals(str) || "\u0000".equals(str)) {
			return true;
		} else if ("".equals(str.trim())) {
			return true;
		}
		return false;
	}

	// 将传入字符串进行MD5编码加密
	public static final String encodeMd5(String oringString) {

		return StringTools.isEmpty(oringString) ? null : DigestUtils.md5Hex(oringString);

	}

	public static boolean versionCompare(String version1, String version2) {
		ComparableVersion v1 = new ComparableVersion(version1);
		ComparableVersion v2 = new ComparableVersion(version2);
		int result = v1.compareTo(v2);
		if (result <= 0) {
			return false;
		}
		return true;
	}

	public static String cleanHtmlTag(String content) {
		if (isEmpty(content)) {
			return content;
		}
		content = content.replace("<", "&lt;");
		content = content.replace("\r\n", "<br>");
		content = content.replace("\n", "<br>");
		return content;

	}
	
	//判断文件后缀工具
	public static String getFileSuffix(String fileName) {
		if (isEmpty(fileName)) {
			return null;
		}
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	//正则判断是否是数字
	public static boolean isNumber(String str) {
		String checkNumber ="^[0-9]+$";
		if (null==str) {
			return false;
		}
		if (!str.matches(checkNumber)) {
			return false;
		}
		return true;
	}
}
