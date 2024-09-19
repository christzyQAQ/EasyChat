package com.easychat.mappers;

import org.apache.ibatis.annotations.Param;

import com.easychat.entity.po.Contact;
import com.easychat.entity.query.ContactQuery;

/**
 * Mapper
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
public interface ContactMapper<T, P> extends BaseMapper {

	
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
	
	int updateByQuery( @Param("contact") Contact contact,@Param("query") ContactQuery query);
}