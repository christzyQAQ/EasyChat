package com.easychat.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.easychat.enums.PageSize;
import com.easychat.entity.po.Session;
import com.easychat.entity.query.SessionQuery;
import com.easychat.entity.query.SimplePage;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.service.SessionService;
import com.easychat.mappers.SessionMapper;

/**
 * ServiceImpl
 * @auther: 系统
 * @date: 2024-07-28 17:23
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService {

	@Resource
	private SessionMapper<Session, SessionQuery> sessionMapper;

	/**
	 * 根据条件查询列表
	 */
	public List<Session> findListByParam(SessionQuery query) {
		return this.sessionMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(SessionQuery query) {
		return this.sessionMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<Session> findListByPage(SessionQuery query) {
		Integer count = this.findCountByParam(query);
		SimplePage page = new SimplePage(query.getPageNo(), query.getPageSize(), count);
		query.setSimplePage(page);
		List<Session> list = this.findListByParam(query);
		PaginationResultVO<Session> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getCountPage(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(Session bean) {
		return this.sessionMapper.insert(bean);
	}

	/**
	 * 新增批量
	 */
	public Integer addBatch(List<Session> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.sessionMapper.insertBatch(listBean);
	}

	/**
	 * 新增批量或修改
	 */
	public Integer addOrUpdateBatch(List<Session> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.sessionMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据SessionId查询
	 */
	public Session selectBySessionId(String sessionId) {
		return this.sessionMapper.selectBySessionId(sessionId);
	}

	/**
	 * 根据SessionId更新
	 */
	public Integer updateBySessionId(Session bean, String sessionId) {
		return this.sessionMapper.updateBySessionId(bean, sessionId);
	}

	/**
	 * 根据SessionId删除
	 */
	public Integer deleteBySessionId(String sessionId) {
		return this.sessionMapper.deleteBySessionId(sessionId);
	}

}