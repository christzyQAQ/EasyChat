package com.easychat.service;

import java.util.List;

import com.easychat.dto.TokenUserInfoDto;
import com.easychat.dto.UserContactSearchResultDto;
import com.easychat.entity.po.Contact;
import com.easychat.entity.po.Info;
import com.easychat.entity.query.ContactQuery;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.enums.UserContactStatusEnum;

/**
 * Service
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
public interface ContactService {

	/**
	 * 根据条件查询列表
	 */
	List<Contact> findListByParam(ContactQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(ContactQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Contact> findListByPage(ContactQuery query);

	/**
	 * 新增
	 */
	Integer add(Contact bean);

	/**
	 * 新增批量
	 */
	Integer addBatch(List<Contact> listBean);

	/**
	 * 新增批量或修改
	 */
	Integer addOrUpdateBatch(List<Contact> listBean);
	
	/**
	 * 根据UserId查询
	 */
	Contact selectByUserId(String userId);

	/**
	 * 根据UserId更新
	 */
	Integer updateByUserId(Contact bean, String userId);

	/**
	 * 根据UserId删除
	 */
	Integer deleteByUserId(String userId);

	/**
	 * 根据UserIdAndContactId查询
	 */
	Contact selectByUserIdAndContactId(String userId, String contactId);

	/**
	 * 根据UserIdAndContactId更新
	 */
	Integer updateByUserIdAndContactId(Contact bean, String userId, String contactId);

	/**
	 * 根据UserIdAndContactId删除
	 */
	Integer deleteByUserIdAndContactId(String userId, String contactId);
	
	 public void updateContactByQuery( Contact contact,ContactQuery contactQuery);
	
	UserContactSearchResultDto searchContact(String userId, String contactId);
	
	
	
	void removeContact(String userId,String contactId, UserContactStatusEnum statusEnum);
	
	

}