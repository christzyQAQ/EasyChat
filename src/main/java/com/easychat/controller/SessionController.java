package com.easychat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.enums.ResponseCodeEnum;
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
	
	

}
