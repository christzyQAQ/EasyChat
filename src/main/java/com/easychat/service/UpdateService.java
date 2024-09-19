package com.easychat.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.easychat.entity.po.Update;
import com.easychat.entity.query.UpdateQuery;
import com.easychat.entity.vo.PaginationResultVO;

/**
 * app发布Service
 * @auther: 系统
 * @date: 2024-07-27 13:27
 */
public interface UpdateService {

	/**
	 * 根据条件查询列表
	 */
	List<Update> findListByParam(UpdateQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(UpdateQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Update> findListByPage(UpdateQuery query);

	/**
	 * 新增
	 */
	Integer add(Update bean);

	/**
	 * 新增批量
	 */
	Integer addBatch(List<Update> listBean);

	/**
	 * 新增批量或修改
	 */
	Integer addOrUpdateBatch(List<Update> listBean);

	/**
	 * 根据Id查询
	 */
	Update selectById(Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateById(Update bean, Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteById(Integer id);

	/**
	 * 根据Version查询
	 */
	Update selectByVersion(String version);

	/**
	 * 根据Version更新
	 */
	Integer updateByVersion(Update bean, String version);

	/**
	 * 根据Version删除
	 */
	Integer deleteByVersion(String version);

	void saveUpdate(Update update, MultipartFile file) throws IOException;
	
	void postUpdate(Integer id, Integer status, String greyScaleUid );
	
	Update getLatestUpdate(String vesion,String uid);

}