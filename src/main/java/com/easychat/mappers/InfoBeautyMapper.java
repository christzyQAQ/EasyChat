package com.easychat.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.easychat.entity.po.InfoBeauty;

/**
 * Mapper
 * @auther: 系统
 * @date: 2024-07-14 22:48
 */
public interface InfoBeautyMapper<T, P> extends BaseMapper {

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
	 * 根据Email查询
	 */
	T selectByEmail (@Param("email") String email);

	/**
	 * 根据Email更新
	 */
	Integer updateByEmail (@Param("bean") T t, @Param("email") String email);

	/**
	 * 根据Email删除
	 */
	Integer deleteByEmail (@Param("email") String email);
}