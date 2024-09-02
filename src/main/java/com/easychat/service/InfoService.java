package com.easychat.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.po.Info;
import com.easychat.entity.query.InfoQuery;
import com.easychat.entity.vo.PaginationResultVO;

/**
 * 用户信息Service
 *
 * @auther: 系统
 * @date: 2024-07-14 22:48
 */

public interface InfoService  {
	
	/**
	 * 根据条件查询列表
	 */
	List<Info> findListByParam(InfoQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(InfoQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Info> findListByPage(InfoQuery query);

	/**
	 * 新增
	 */
	Integer add(Info bean);

	/**
	 * 新增批量
	 */
	Integer addBatch(List<Info> listBean);

	/**
	 * 新增批量或修改
	 */
	Integer addOrUpdateBatch(List<Info> listBean);

	/**
	 * 根据UserId查询
	 */
	Info selectByUserId(String userId);

	/**
	 * 根据UserId更新
	 */
	Integer updateByUserId(Info bean, String userId);

	/**
	 * 根据UserId删除
	 */
	Integer deleteByUserId(String userId);

	/**
	 * 根据Email查询
	 */
	Info selectByEmail(String email);

	/**
	 * 根据Email更新
	 */
	Integer updateByEmail(Info bean, String email);

	/**
	 * 根据Email删除
	 */
	Integer deleteByEmail(String email);

	/**
	 * 注册
	 * @return 
	 */
	void register(String email, String nickName, String password);
	
	TokenUserInfoDto login(String email, String password);
	
	TokenUserInfoDto getTokenUserInfoDto(Info info);

	void updateinfo (Info info,MultipartFile avatarFile,MultipartFile avatarCover ) throws IOException;
	
	void modifyPassword(String oldpassword, String newPassword,TokenUserInfoDto tokenUserInfoDto);
	
	void updateUserStatus(Integer status, String userId);
	
	void forceOffLine(String userId);


}