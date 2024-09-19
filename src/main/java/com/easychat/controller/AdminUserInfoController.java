package com.easychat.controller;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 25, 2024 6:05:09 PM
* @ClassName:AdminController.java

*/

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import javax.validation.constraints.NotEmpty;
import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.query.InfoQuery;
import com.easychat.service.InfoService;

@RestController("adminUserInfoController")
@RequestMapping("/admin/")
public class AdminUserInfoController extends BaseController {

	@Resource
	private InfoService infoService;

	@RequestMapping("loadUser")
	@GlobalInterceptor(checkadmin = true)
	public ResponseVO loadUser(InfoQuery infoQuery) {
		infoQuery.setOrderBy("create_time desc");
		PaginationResultVO resultVO = infoService.findListByPage(infoQuery);
		return getSuccessResponseVO(resultVO);
	}

	@RequestMapping("updateUserStatus")
	@GlobalInterceptor(checkadmin = true)
	public ResponseVO updateUserStatus(@NotEmpty Integer status, @NotEmpty String userId) {
		infoService.updateUserStatus(status, userId);
		return getSuccessResponseVO("已成功修改");
	}

	@RequestMapping("forceOffLine")
	@GlobalInterceptor(checkadmin = true)
	public ResponseVO forceOffLine(@NotEmpty String userId) {
		infoService.forceOffLine(userId);
		return getSuccessResponseVO("null");
	}
}
