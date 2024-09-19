package com.easychat.mappers;

import org.apache.ibatis.annotations.Param;

import com.easychat.entity.po.Update;

/**
 * app发布Mapper
 * @auther: 系统
 * @date: 2024-07-27 13:27
 */
public interface UpdateMapper<T, P> extends BaseMapper {

	/**
	 * 根据Id查询
	 */
	T selectById (@Param("id") Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateById (@Param("bean") T t, @Param("id") Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteById (@Param("id") Integer id);

	/**
	 * 根据Version查询
	 */
	T selectByVersion (@Param("version") String version);

	/**
	 * 根据Version更新
	 */
	Integer updateByVersion (@Param("bean") T t, @Param("version") String version);

	/**
	 * 根据Version删除
	 */
	Integer deleteByVersion (@Param("version") String version);

	Update selectLatestUpdate(@Param("version") String vesion, @Param("uid") String uid);

}