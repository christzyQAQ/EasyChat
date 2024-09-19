package com.easychat.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * Mapper
 * @auther: 系统
 * @date: 2024-07-28 17:23
 */
public interface SessionMapper<T, P> extends BaseMapper {

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

}