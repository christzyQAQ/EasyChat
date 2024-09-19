package com.easychat.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easychat.dto.UserContactSearchResultDto;
import com.easychat.entity.po.Contact;
import com.easychat.entity.po.ContactApply;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.po.Info;
import com.easychat.entity.query.ContactApplyQuery;
import com.easychat.entity.query.ContactQuery;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.entity.query.InfoQuery;
import com.easychat.entity.query.SimplePage;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.enums.UserContactStatusEnum;
import com.easychat.enums.UserContactTypeEnum;
import com.easychat.service.ContactApplyService;
import com.easychat.service.ContactService;
import com.easychat.utils.CopyTools;
import com.easychat.websocket.MessageHandler;

import com.easychat.mappers.ContactApplyMapper;
import com.easychat.mappers.ContactMapper;
import com.easychat.mappers.GroupInfoMapper;
import com.easychat.mappers.InfoMapper;

/**
 * ServiceImpl
 * 
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService {

	@Resource
	private ContactMapper<Contact, ContactQuery> contactMapper;

	@Resource
	private InfoMapper<Info, InfoQuery> infoMapper;

	@Resource
	private GroupInfoMapper<GroupInfo, GroupInfoQuery> groupInfoMapper;

	@Resource
	private ContactApplyMapper<ContactApply, ContactApplyQuery> contactApplyMapper;

	@Resource
	private ContactApplyService contactApplyService;
	
	@Resource
	private MessageHandler messageHandler;

	/**
	 * 根据条件查询列表
	 */
	public List<Contact> findListByParam(ContactQuery query) {
		return this.contactMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(ContactQuery query) {
		return this.contactMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<Contact> findListByPage(ContactQuery query) {
		Integer count = this.findCountByParam(query);
		SimplePage page = new SimplePage(query.getPageNo(), query.getPageSize(), count);
		query.setSimplePage(page);
		List<Contact> list = this.findListByParam(query);
		PaginationResultVO<Contact> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(),
				page.getCountPage(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(Contact bean) {
		return this.contactMapper.insert(bean);
	}

	/**
	 * 新增批量
	 */
	public Integer addBatch(List<Contact> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.contactMapper.insertBatch(listBean);
	}

	/**
	 * 新增批量或修改
	 */
	public Integer addOrUpdateBatch(List<Contact> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.contactMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据UserId查询
	 */
	public Contact selectByUserId(String userId) {
		return this.contactMapper.selectByUserId(userId);
	}

	/**
	 * 根据UserId更新
	 */
	public Integer updateByUserId(Contact bean, String userId) {
		return this.contactMapper.updateByUserId(bean, userId);
	}

	/**
	 * 根据UserId删除
	 */
	public Integer deleteByUserId(String userId) {
		return this.contactMapper.deleteByUserId(userId);
	}

	/**
	 * 根据UserIdAndContactId查询
	 */
	public Contact selectByUserIdAndContactId(String userId, String contactId) {
		return this.contactMapper.selectByUserIdAndContactId(userId, contactId);
	}

	/**
	 * 根据UserIdAndContactId更新
	 */
	public Integer updateByUserIdAndContactId(Contact bean, String userId, String contactId) {
		return this.contactMapper.updateByUserIdAndContactId(bean, userId, contactId);
	}

	/**
	 * 根据UserIdAndContactId删除
	 */
	public Integer deleteByUserIdAndContactId(String userId, String contactId) {
		return this.contactMapper.deleteByUserIdAndContactId(userId, contactId);
	}

	 public void updateContactByQuery( Contact contact,ContactQuery contactQuery) {
	        contactMapper.updateByQuery(contact, contactQuery);
	    }

	// 搜索联系人
	@Override
	public UserContactSearchResultDto searchContact(String userId, String contactId) {
		UserContactTypeEnum typeEnum = UserContactTypeEnum.getByprefix(contactId);
		if (typeEnum == null) {
			return null;
		}
		UserContactSearchResultDto resultDto = new UserContactSearchResultDto();
		switch (typeEnum) {
		case USER:
			Info info = infoMapper.selectByUserId(contactId);
			if (info == null) {
				return null;
			}
			resultDto = CopyTools.copy(info, UserContactSearchResultDto.class);

			break;
		case GROUP:
			GroupInfo groupInfo = groupInfoMapper.selectByGroupId(contactId);
			if (groupInfo == null) {
				return null;
			}
			resultDto.setNickName(groupInfo.getGroupName());
			;
			break;
		}
		resultDto.setContactType(typeEnum.toString());
		resultDto.setContactId(contactId);
		if (contactId == userId) {
			resultDto.setStatus(UserContactStatusEnum.FRIEND.getStatus());
			return resultDto;
		}
		// 查询是否是好友
		Contact contact = this.contactMapper.selectByUserIdAndContactId(userId, contactId);
		resultDto.setStatus(contact == null ? null : contact.getStatus());

		return resultDto;
	}



	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeContact(String userId, String contactId, UserContactStatusEnum statusEnum) {
		// 移除好友
		Contact contact = new Contact();
		contact.setStatus(statusEnum.getStatus());
		contactMapper.updateByUserIdAndContactId(contact, userId, contactId);

		// 将好友中的自己移除
		contact = new Contact();
		if (UserContactStatusEnum.DEL == statusEnum) {
			contact.setStatus(UserContactStatusEnum.DEL_BE.getStatus());
		} else if (UserContactStatusEnum.BLACKLIST == statusEnum) {
			contact.setStatus(UserContactStatusEnum.BLACKLIST_BE.getStatus());
		}
		contactMapper.updateByUserIdAndContactId(contact, contactId, userId);
		// TODO 从我的好友列表缓存中删除好友
		// TODO 从好友缓存列表中删除我

	}

}