package com.easychat.service;

import java.util.List;
import com.easychat.entity.po.SessionUser;
import com.easychat.entity.query.SessionUserQuery;
import com.easychat.entity.vo.PaginationResultVO;

/**
 * 会话用户Service
 * @auther: 系统
 * @date: 2024-07-28 17:23
 */
public interface SessionUserService {

	/**
	 * 根据条件查询列表
	 */
	List<SessionUser> findListByParam(SessionUserQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(SessionUserQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<SessionUser> findListByPage(SessionUserQuery query);

	/**
	 * 新增
	 */
	Integer add(SessionUser bean);

	/**
	 * 新增批量
	 */
	Integer addBatch(List<SessionUser> listBean);

	/**
	 * 新增批量或修改
	 */
	Integer addOrUpdateBatch(List<SessionUser> listBean);

	/**
	 * 根据UserIdAndContactId查询
	 */
	SessionUser selectByUserIdAndContactId(String userId, String contactId);

	/**
	 * 根据UserIdAndContactId更新
	 */
	Integer updateByUserIdAndContactId(SessionUser bean, String userId, String contactId);

	/**
	 * 根据UserIdAndContactId删除
	 */
	Integer deleteByUserIdAndContactId(String userId, String contactId);

	/**
	 * 根据UserId查询
	 */
	SessionUser selectByUserId(String userId);

	/**
	 * 根据UserId更新
	 */
	Integer updateByUserId(SessionUser bean, String userId);

	/**
	 * 根据UserId删除
	 */
	Integer deleteByUserId(String userId);

	/**
	 * 根据SessionId查询
	 */
	SessionUser selectBySessionId(String sessionId);

	/**
	 * 根据SessionId更新
	 */
	Integer updateBySessionId(SessionUser bean, String sessionId);

	/**
	 * 根据SessionId删除
	 */
	Integer deleteBySessionId(String sessionId);
	
	public void  updateRedundanceInfo(String contactNameUpdate, String contactId);

}