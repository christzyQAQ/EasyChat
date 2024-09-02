package com.easychat.mappers;

import org.apache.ibatis.annotations.Param;

import com.easychat.entity.po.SessionUser;
import com.easychat.entity.query.SessionQuery;
import com.easychat.entity.query.SessionUserQuery;

/**
 * 会话用户Mapper
 * @auther: 系统
 * @date: 2024-07-28 17:23
 */
public interface SessionUserMapper<T, P> extends BaseMapper {

	/**
	 * 根据UserIdAndContactId查询
	 */
	T selectByUserIdAndContactId (@Param("userId") String userId, @Param("contactId") String contactId);

	/**
	 * 根据UserIdAndContactId更新
	 */
	Integer updateByUserIdAndContactId (@Param("bean") T t, @Param("userId") String userId, @Param("contactId") String contactId);

	/**
	 * 根据UserIdAndContactId删除
	 */
	Integer deleteByUserIdAndContactId (@Param("userId") String userId, @Param("contactId") String contactId);

	/**
	 * 根据UserId查询
	 */
	T selectByUserId (@Param("userId") String userId);

	/**
	 * 根据UserId更新
	 */
	Integer updateByUserId (@Param("bean") T t, @Param("userId") String userId);

	/**
	 * 根据UserId删除
	 */
	Integer deleteByUserId (@Param("userId") String userId);

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
	T selectByContactId (@Param("ContactId") String contactId);

	/**
	 * 根据ContactId更新
	 */
	Integer updateByContactId (@Param("bean") T t, @Param("contactId") String contactId);

	/**
	 * 根据ContactId删除
	 */
	Integer deleteByContactId (@Param("contactId") String contactId);
	
	Integer updateByParam(@Param("bean")T t, @Param("sessionUserQuery") SessionUserQuery sessionUserQuery);
	
	
	
	

}