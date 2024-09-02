package com.easychat.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.easychat.dto.MessageSendDto;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.po.Message;
import com.easychat.entity.query.MessageQuery;
import com.easychat.entity.vo.PaginationResultVO;

/**
 * 聊天信息表Service
 * @auther: 系统
 * @date: 2024-07-28 17:23
 */
public interface MessageService {

	/**
	 * 根据条件查询列表
	 */
	List<Message> findListByParam(MessageQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(MessageQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVO<Message> findListByPage(MessageQuery query);

	/**
	 * 新增
	 */
	Integer add(Message bean);

	/**
	 * 新增批量
	 */
	Integer addBatch(List<Message> listBean);

	/**
	 * 新增批量或修改
	 */
	Integer addOrUpdateBatch(List<Message> listBean);

	/**
	 * 根据MessageId查询
	 */
	Message selectByMessageId(Long messageId);

	/**
	 * 根据MessageId更新
	 */
	Integer updateByMessageId(Message bean, Long messageId);

	/**
	 * 根据MessageId删除
	 */
	Integer deleteByMessageId(Long messageId);

	/**
	 * 根据SessionId查询
	 */
	Message selectBySessionId(String sessionId);

	/**
	 * 根据SessionId更新
	 */
	Integer updateBySessionId(Message bean, String sessionId);

	/**
	 * 根据SessionId删除
	 */
	Integer deleteBySessionId(String sessionId);

	/**
	 * 根据ContactId查询
	 */
	Message selectByContactId(String contactId);

	/**
	 * 根据ContactId更新
	 */
	Integer updateByContactId(Message bean, String contactId);

	/**
	 * 根据ContactId删除
	 */
	Integer deleteByContactId(String contactId);

	/**
	 * 根据SendUserId查询
	 */
	Message selectBySendUserId(String sendUserId);

	/**
	 * 根据SendUserId更新
	 */
	Integer updateBySendUserId(Message bean, String sendUserId);

	/**
	 * 根据SendUserId删除
	 */
	Integer deleteBySendUserId(String sendUserId);

	/**
	 * 根据SendTime查询
	 */
	Message selectBySendTime(Long sendTime);

	/**
	 * 根据SendTime更新
	 */
	Integer updateBySendTime(Message bean, Long sendTime);

	/**
	 * 根据SendTime删除
	 */
	Integer deleteBySendTime(Long sendTime);
	
	MessageSendDto saveMessage(Message message,TokenUserInfoDto tokenUserInfoDto);
	
	void saveMessageFile(String userId,Long messageId,MultipartFile file,MultipartFile coverFile);

	File downloadFile(TokenUserInfoDto tokenUserInfoDto,Long fileId,boolean showcover);
}