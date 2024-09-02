package com.easychat.service;

import java.util.List;

import com.easychat.dto.TokenUserInfoDto;
import com.easychat.dto.UserContactSearchResultDto;
import com.easychat.entity.po.ContactApply;
import com.easychat.entity.query.ContactApplyQuery;
import com.easychat.entity.vo.PaginationResultVO;


/**
 * Service
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
public interface ContactApplyService {

	/**
	 * 根据条件查询列表
	 */
	List<ContactApply> findListByParam(ContactApplyQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(ContactApplyQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<ContactApply> findListByPage(ContactApplyQuery query);

	/**
	 * 新增
	 */
	Integer add(ContactApply bean);

	/**
	 * 新增批量
	 */
	Integer addBatch(List<ContactApply> listBean);

	/**
	 * 新增批量或修改
	 */
	Integer addOrUpdateBatch(List<ContactApply> listBean);

	/**
	 * 根据ApplyId查询
	 */
	ContactApply selectByApplyId(Integer applyId);

	/**
	 * 根据ApplyId更新
	 */
	Integer updateByApplyId(ContactApply bean, Integer applyId);

	/**
	 * 根据ApplyId删除
	 */
	Integer deleteByApplyId(Integer applyId);
	
	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndContactId查询
	 */
	ContactApply selectByApplyUserIdAndReceiveUserIdAndContactId(String applyUserId, String receiveUserId, String contactId);

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndContactId更新
	 */
	Integer updateByApplyUserIdAndReceiveUserIdAndContactId(ContactApply bean, String applyUserId, String receiveUserId, String contactId);

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndContactId删除
	 */
	Integer deleteByApplyUserIdAndReceiveUserIdAndContactId(String applyUserId, String receiveUserId, String contactId);
	
	Integer applyAdd(TokenUserInfoDto tokenUserInfoDto,String contactId, String applyInfo);

	void dealWithApply (String userId, Integer applyId, Integer status); 
	
	void addContact(String applyUserId,String receiveUserId,String contactId,Integer contactType,String applyInfo);
	
	void addContact4Robot(String userId);
		
	
	
	
	
	

}