package com.easychat.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.dto.UserContactSearchResultDto;
import com.easychat.entity.po.Contact;
import com.easychat.entity.po.Info;
import com.easychat.entity.query.ContactApplyQuery;
import com.easychat.entity.query.ContactQuery;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.entity.vo.UserInfoVO;
import com.easychat.enums.PageSize;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.enums.UserContactApplyStatusEnum;
import com.easychat.enums.UserContactStatusEnum;
import com.easychat.enums.UserContactTypeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.service.ContactApplyService;
import com.easychat.service.ContactService;
import com.easychat.service.InfoService;
import com.easychat.utils.CopyTools;

import jodd.util.ArraysUtil;

/**
 * Controller
 * 
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
@RestController
@RequestMapping("/group/")
public class ContactController extends BaseController {

	@Resource
	private ContactService contactService;

	@Resource
	private InfoService infoService;

	@Resource
	private ContactApplyService contactApplyService;

	// 搜索好友或者群聊
	@RequestMapping("search")
	@GlobalInterceptor
	public ResponseVO searchContact(HttpServletRequest request, @NotEmpty String contactId) {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		UserContactSearchResultDto resultDto = contactService.searchContact(tokenUserInfoDto.getUserId(), contactId);		
		return getSuccessResponseVO(resultDto);

	}

	// 发送好友或群组添加申请与直接添加好友或群组
	@RequestMapping("applyAdd")
	@GlobalInterceptor
	public ResponseVO applyAdd(HttpServletRequest request, @NotEmpty String contactId, @NotEmpty Integer contactType,
			String applyInfo) {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		Integer joinType = contactApplyService.applyAdd(tokenUserInfoDto, contactId, applyInfo);
		return getSuccessResponseVO(joinType);

	}

	// 获取好友申请列表
	@RequestMapping("loadApply")
	@GlobalInterceptor
	public ResponseVO loadApply(HttpServletRequest request, Integer pageNo) {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		String recievedId = tokenUserInfoDto.getUserId();
		ContactApplyQuery applyQuery = new ContactApplyQuery();
		applyQuery.setOrderBy("last_apply_time desc");
		applyQuery.setReceiveUserId(recievedId);
		applyQuery.setPageNo(pageNo);
		applyQuery.setPageSize(PageSize.SIZE_20.getSize());
		applyQuery.setGetQueryContactInfo(true);
		PaginationResultVO resultVO = contactApplyService.findListByPage(applyQuery);
		return getSuccessResponseVO(resultVO);
	}

	// 处理联系人申请
	@RequestMapping("withApply")
	@GlobalInterceptor
	public ResponseVO WithApply(HttpServletRequest request, @NotNull Integer applyId, @NotNull Integer status) {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		this.contactApplyService.dealWithApply(tokenUserInfoDto.getUserId(), applyId, status);
		String result = UserContactApplyStatusEnum.getByStatus(status).getDesc();
		return getSuccessResponseVO(result);
	}

	// 获取联系人列表
	@RequestMapping("loadContact")
	@GlobalInterceptor
	public ResponseVO loadContact(HttpServletRequest request, String contactType) {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getByName(contactType);
		if (null == contactType) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}

		String userId = tokenUserInfoDto.getUserId();
		ContactQuery contactQuery = new ContactQuery();
		contactQuery.setOrderBy("creat_time desc");
		contactQuery.setUserId(userId);
		contactQuery.setContactType(contactTypeEnum.getType());
		// 查询联系人需要关联联系人昵称等信息
		if (contactTypeEnum.USER == contactTypeEnum) {
			contactQuery.setQueryContactUserInfo(true);

		} else if (contactTypeEnum.GROUP == contactTypeEnum) {
			contactQuery.setQueryGroupInfo(true);
			contactQuery.setExcludeMyGroup(true);
		}

		contactQuery.setStatusArray(new Integer[] { UserContactStatusEnum.FRIEND.getStatus(),
				UserContactStatusEnum.BLACKLIST_BE.getStatus(), UserContactStatusEnum.DEL_BE.getStatus() });

		contactQuery.setOrderBy("last_update_time desc");
		List<Contact> contactList = contactService.findListByParam(contactQuery);
		return getSuccessResponseVO(contactList);

	}

	// 获取联系人信息(非群聊)
	@RequestMapping("getContactUserInfo")
	@GlobalInterceptor
	public ResponseVO getContactUserInfo(HttpServletRequest request, @NotNull String contactId) {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);

		Contact contact = contactService.selectByUserIdAndContactId(tokenUserInfoDto.getUserId(), contactId);
		if (contact == null||!ArraysUtil.contains(new Integer[] {
				UserContactStatusEnum.FRIEND.getStatus(),
				UserContactStatusEnum.BLACKLIST_BE.getStatus(),
				UserContactStatusEnum.DEL_BE.getStatus()
		}, contact.getStatus())) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		Info info = infoService.selectByUserId(contactId);
		UserInfoVO userInfoVO = CopyTools.copy(info, UserInfoVO.class);
		return getSuccessResponseVO(userInfoVO);
	}

	// 删除联系人
	@RequestMapping("delContact")
	@GlobalInterceptor
	public ResponseVO delContact(HttpServletRequest request, @NotNull String contactId) {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		contactService.removeContact(tokenUserInfoDto.getUserId(), contactId, UserContactStatusEnum.DEL);		
		return getSuccessResponseVO(null);
	}
	
	// 拉黑联系人
		@RequestMapping("addContact2BlackList")
		@GlobalInterceptor
		public ResponseVO addContact2BlackList(HttpServletRequest request, @NotNull String contactId) {
			TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
			contactService.removeContact(tokenUserInfoDto.getUserId(), contactId, UserContactStatusEnum.BLACKLIST);		
			return getSuccessResponseVO(null);
		}
}
