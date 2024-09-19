package com.easychat.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.po.Contact;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.query.ContactQuery;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.entity.vo.GroupInfoVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.enums.GroupStatusEnum;
import com.easychat.enums.UserContactStatusEnum;
import com.easychat.exception.BusinessException;
import com.easychat.service.ContactService;
import com.easychat.service.GroupInfoService;

/**
 * Controller
 * @auther: 系统
 * @date: 2024-07-21 16:19
 */
@RestController
@RequestMapping("/group/")
@Validated



public class GroupInfoController extends BaseController {

	@Resource
	private GroupInfoService groupInfoService;
	
	@Resource
	private ContactService contactService;

	@RequestMapping("/saveGroup")
	@GlobalInterceptor//校验登录注解
	public ResponseVO saveGroup(HttpServletRequest request,String groupId,
									@NotEmpty String groupName,
									String groupNotice,
									@NotNull Integer joinType,
									MultipartFile avatarFile,
									MultipartFile avatarCover
									) throws IOException{
		TokenUserInfoDto tokenUserInfoDto=getTokenUserInfo(request);
		GroupInfo groupInfo=new GroupInfo();
		groupInfo.setGroupId(groupId);
		groupInfo.setGroupOwnerId(tokenUserInfoDto.getUserId());
		groupInfo.setGroupName(groupName);
		groupInfo.setGroupNotice(groupNotice);
		groupInfo.setJoinType(joinType);
		groupInfo.setStatus(GroupStatusEnum.NORMAL.getStatus());
		this.groupInfoService.saveGroup(groupInfo, avatarFile, avatarCover);
		
		
		return getSuccessResponseVO(null);
				
	} 
	
	
	/**
	 * 获取我创建的群组
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadMyGroup")
	public ResponseVO loadMyGroup(HttpServletRequest request) {
		TokenUserInfoDto tokenUserInfoDto=getTokenUserInfo(request);
		GroupInfoQuery groupInfoQuery=new GroupInfoQuery();
		groupInfoQuery.setGroupOwnerId(tokenUserInfoDto.getUserId());
		groupInfoQuery.setOrderBy("creat_time desc");
		List<GroupInfo> groupInfoList=this.groupInfoService.findListByParam(groupInfoQuery);
		
		return getSuccessResponseVO(groupInfoList);
		
	}
	
		
	
	@RequestMapping("/getGroupInfo")
	@GlobalInterceptor
	public ResponseVO  getGroupInfo(
								HttpServletRequest request,
								@NotEmpty String groupId)
	{		
		GroupInfo groupInfo=getGroupDetailCommen(request, groupId);
		ContactQuery contactQuery=new ContactQuery();
		contactQuery.setContactId(groupId);
		Integer groupMemberCount=this.contactService.findCountByParam(contactQuery);
		groupInfo.setMemberCount(groupMemberCount);
		return getSuccessResponseVO(groupInfo);
				
	}

	private GroupInfo getGroupDetailCommen(HttpServletRequest request,
											@NotEmpty String groupId) {

		TokenUserInfoDto tokenUserInfoDto=getTokenUserInfo(request);
		Contact contact =this.contactService.selectByUserIdAndContactId(tokenUserInfoDto.getUserId(), groupId);
		if(contact==null||!UserContactStatusEnum.FRIEND.getStatus().equals(contact.getStatus())) {
			throw new BusinessException("你不在该群聊或群聊不存在或该群聊已解散");
		}
		GroupInfo groupInfo= this.groupInfoService.selectByGroupId(groupId);
		
		if (groupInfo==null||!GroupStatusEnum.NORMAL.getStatus().equals(groupInfo.getStatus())) {
			throw new BusinessException("群聊不存在或群聊已解散");
			
		}
		return groupInfo;
		
	}
	
	@RequestMapping("/getGroupInfo4Chat")
	@GlobalInterceptor
	public ResponseVO  getGroupInfo4Chat(
								HttpServletRequest request,
								@NotEmpty String groupId)
	{		
		GroupInfo groupInfo=getGroupDetailCommen(request, groupId);
		ContactQuery contactQuery =new ContactQuery();
		contactQuery.setQueryUserInfo(true);
		contactQuery.setOrderBy("create_time desc");
		contactQuery.setStatus(groupInfo.getStatus());
		List<Contact> contactList = this.contactService.findListByParam(contactQuery);
		GroupInfoVO groupInfoVO =new GroupInfoVO();
		groupInfoVO.setGroupInfo(groupInfo);
		groupInfoVO.setContactList(contactList);
		
		return getSuccessResponseVO(groupInfoVO);
				
	}

}


