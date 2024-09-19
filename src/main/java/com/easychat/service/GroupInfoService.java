package com.easychat.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.entity.vo.PaginationResultVO;

/**
 * Service
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
public interface GroupInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<GroupInfo> findListByParam(GroupInfoQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(GroupInfoQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<GroupInfo> findListByPage(GroupInfoQuery query);

	/**
	 * 新增
	 */
	Integer add(GroupInfo bean);

	/**
	 * 新增批量
	 */
	Integer addBatch(List<GroupInfo> listBean);

	/**
	 * 新增批量或修改
	 */
	Integer addOrUpdateBatch(List<GroupInfo> listBean);

	/**
	 * 根据GroupId查询
	 */
	GroupInfo selectByGroupId(String groupId);

	/**
	 * 根据GroupId更新
	 */
	Integer updateByGroupId(GroupInfo bean, String groupId);

	/**
	 * 根据GroupId删除
	 */
	Integer deleteByGroupId(String groupId);
	
	void saveGroup(GroupInfo groupinfo, MultipartFile avatarFile, MultipartFile avatarCover) throws IOException;
	
	void dissolutionGroup(String groupOwnerId,String groupId);

}