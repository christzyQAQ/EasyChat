package com.easychat.service;

import java.util.List;
import com.easychat.entity.po.InfoBeauty;
import com.easychat.entity.query.InfoBeautyQuery;
import com.easychat.entity.vo.PaginationResultVO;

/**
 * Service
 * @auther: 系统
 * @date: 2024-07-14 22:48
 */
public interface InfoBeautyService {

	/**
	 * 根据条件查询列表
	 */
	List<InfoBeauty> findListByParam(InfoBeautyQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(InfoBeautyQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<InfoBeauty> findListByPage(InfoBeautyQuery query);

	/**
	 * 新增
	 */
	Integer add(InfoBeauty bean);

	/**
	 * 新增批量
	 */
	Integer addBatch(List<InfoBeauty> listBean);

	/**
	 * 新增批量或修改
	 */
	Integer addOrUpdateBatch(List<InfoBeauty> listBean);

	/**
	 * 根据Id查询
	 */
	InfoBeauty selectById(Integer id);

	/**
	 * 根据Id更新
	 */
	Integer updateById(InfoBeauty bean, Integer id);

	/**
	 * 根据Id删除
	 */
	Integer deleteById(Integer id);

	/**
	 * 根据UserId查询
	 */
	InfoBeauty selectByUserId(String userId);

	/**
	 * 根据UserId更新
	 */
	Integer updateByUserId(InfoBeauty bean, String userId);

	/**
	 * 根据UserId删除
	 */
	Integer deleteByUserId(String userId);

	/**
	 * 根据Email查询
	 */
	InfoBeauty selectByEmail(String email);

	/**
	 * 根据Email更新
	 */
	Integer updateByEmail(InfoBeauty bean, String email);

	/**
	 * 根据Email删除
	 */
	Integer deleteByEmail(String email);
	
	void saveAccount(InfoBeauty beauty);

}