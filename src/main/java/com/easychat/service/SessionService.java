package com.easychat.service;

import java.util.List;
import com.easychat.entity.po.Session;
import com.easychat.entity.query.SessionQuery;
import com.easychat.entity.vo.PaginationResultVO;

/**
 * Service
 * @auther: 系统
 * @date: 2024-07-28 17:23
 */
public interface SessionService {

	/**
	 * 根据条件查询列表
	 */
	List<Session> findListByParam(SessionQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(SessionQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Session> findListByPage(SessionQuery query);

	/**
	 * 新增
	 */
	Integer add(Session bean);

	/**
	 * 新增批量
	 */
	Integer addBatch(List<Session> listBean);

	/**
	 * 新增批量或修改
	 */
	Integer addOrUpdateBatch(List<Session> listBean);

	/**
	 * 根据SessionId查询
	 */
	Session selectBySessionId(String sessionId);

	/**
	 * 根据SessionId更新
	 */
	Integer updateBySessionId(Session bean, String sessionId);

	/**
	 * 根据SessionId删除
	 */
	Integer deleteBySessionId(String sessionId);

}