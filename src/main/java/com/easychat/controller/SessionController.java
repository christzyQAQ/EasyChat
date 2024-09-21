package com.easychat.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.po.Session;
import com.easychat.entity.po.SessionUser;
import com.easychat.entity.query.InfoQuery;
import com.easychat.entity.query.SessionQuery;
import com.easychat.entity.query.SessionUserQuery;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.mappers.SessionUserMapper;
import com.easychat.service.SessionUserService;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Sep 19, 202410:05:02 AM
* @ClassName:SessionController.java

*/
@RestController
@RequestMapping("/session/")
public class SessionController extends BaseController {

	@Resource
	private SessionUserService sessionUserService;
	
	private SessionUserMapper sessionUserMapper;
	
	
	
	/**
	 * 置顶会话
	 * @param request
	 * @param contactId
	 * @return
	 */
	@RequestMapping("pinSession")
	@GlobalInterceptor
	public ResponseVO pinSession(HttpServletRequest request,@NotNull String contactId){
		//通过用户与联系人id查找sessionId
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		String userId= tokenUserInfoDto.getUserId();
		sessionUserService.pinSession(userId,contactId);		
		return getSuccessResponseVO(ResponseCodeEnum.CODE_200.getMsg());
	}
	
	
	/**
	 * 
	 * 取消置顶
	 * @param request
	 * @param contactId
	 * @return
	 */
	@RequestMapping("unPinSession")
	@GlobalInterceptor
	public ResponseVO unPinSession(HttpServletRequest request,@NotNull String contactId){
		//通过用户与联系人id查找sessionId
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		String userId= tokenUserInfoDto.getUserId();
		sessionUserService.unPinSession(userId,contactId);		
		return getSuccessResponseVO(ResponseCodeEnum.CODE_200.getMsg());
	}
	
	@RequestMapping("loadSessionList")
	@GlobalInterceptor
	public ResponseVO loadDataList(HttpServletRequest request) {
		TokenUserInfoDto tokenUserInfoDto =getTokenUserInfo(request);
		String userId=tokenUserInfoDto.getUserId();
		//联表查询会记录
		SessionUserQuery sessionUserQuery =new SessionUserQuery();
		sessionUserQuery.setUserId(userId);
		sessionUserQuery.setOrderBy("is_pinned DESC, last_recieve_time DESC");	
		List result = sessionUserService.findListByParam(sessionUserQuery);
		return getSuccessResponseVO(result);
	}
	
	

}
