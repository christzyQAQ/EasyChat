package com.easychat.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 聊天信息表Mapper
 * @auther: 系统
 * @date: 2024-07-28 17:22
 */
public interface MessageMapper<T, P> extends BaseMapper {

	/**
	 * 根据MessageId查询
	 */
	T selectByMessageId (@Param("messageId") Long messageId);

	/**
	 * 根据MessageId更新
	 */
	Integer updateByMessageId (@Param("bean") T t, @Param("messageId") Long messageId);

	/**
	 * 根据MessageId删除
	 */
	Integer deleteByMessageId (@Param("messageId") Long messageId);

	/**
	 * 根据SessionId查询
	 */
	T selectBySessionId (@Param("sessionId") String sessionId);

	/**
	 * 根据SessionId更新
	 */
	Integer updateBySessionId (@Param("bean") T t, @Param("sessionId") String sessionId);

	/**
	 * 根据SessionId删除
	 */
	Integer deleteBySessionId (@Param("sessionId") String sessionId);

	/**
	 * 根据ContactId查询
	 */
	T selectByContactId (@Param("contactId") String contactId);

	/**
	 * 根据ContactId更新
	 */
	Integer updateByContactId (@Param("bean") T t, @Param("contactId") String contactId);

	/**
	 * 根据ContactId删除
	 */
	Integer deleteByContactId (@Param("contactId") String contactId);

	/**
	 * 根据SendUserId查询
	 */
	T selectBySendUserId (@Param("sendUserId") String sendUserId);

	/**
	 * 根据SendUserId更新
	 */
	Integer updateBySendUserId (@Param("bean") T t, @Param("sendUserId") String sendUserId);

	/**
	 * 根据SendUserId删除
	 */
	Integer deleteBySendUserId (@Param("sendUserId") String sendUserId);

	/**
	 * 根据SendTime查询
	 */
	T selectBySendTime (@Param("sendTime") Long sendTime);

	/**
	 * 根据SendTime更新
	 */
	Integer updateBySendTime (@Param("bean") T t, @Param("sendTime") Long sendTime);

	/**
	 * 根据SendTime删除
	 */
	Integer deleteBySendTime (@Param("sendTime") Long sendTime);

}