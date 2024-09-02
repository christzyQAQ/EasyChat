package com.easychat.controller;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 26, 202410:33:48 AM
* @ClassName:AdminGroupController.java

*/
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.annotation.GlobalIntercepter;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.service.GroupInfoService;


@RestController("adminGroupController")
@RequestMapping("/admin/")
public class AdminGroupController extends BaseController {

	@Resource
	private GroupInfoService groupInfoService; 
	
	@RequestMapping("loadGroup")
	@GlobalIntercepter(checkadmin = true)
	public ResponseVO loadGroup(GroupInfoQuery groupInfoQuery) {		
		groupInfoQuery.setOrderBy("create_time desc");
		groupInfoQuery.setQueryGroupOwner(true);
		groupInfoQuery.setQueryGroupMember(true);		
		PaginationResultVO resultVO =groupInfoService.findListByPage(groupInfoQuery);
		return getSuccessResponseVO(resultVO);				
	}
	
	@RequestMapping("dissolutionGroup")
	@GlobalIntercepter(checkadmin = true)
	public ResponseVO dissolutionGroup(@NotEmpty String groupId) {	
		GroupInfo groupInfo=groupInfoService.selectByGroupId(groupId);
		if(null==groupInfo) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		groupInfoService.dissolutionGroup(groupInfo.getGroupOwnerId(), groupId);
		return getSuccessResponseVO(null);				
	}
	
	
	
	
}
