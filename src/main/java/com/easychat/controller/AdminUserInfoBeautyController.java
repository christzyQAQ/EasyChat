package com.easychat.controller;
/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 25, 20246:05:09 PM
* @ClassName:AdminController.java

*/

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.po.InfoBeauty;
import com.easychat.entity.query.InfoBeautyQuery;
import com.easychat.service.InfoBeautyService;

@RestController("adminUserInfoBeautyController")
@RequestMapping("/admin/")
public class AdminUserInfoBeautyController extends BaseController {

	@Resource
	private InfoBeautyService infoBeautyService;
	
	
	
	@RequestMapping("loadBeautyAccountList")
	@GlobalInterceptor(checkadmin = true)
	public ResponseVO loadBeautyAccountList(InfoBeautyQuery infoBeautyQuery) {
		infoBeautyQuery.setOrderBy("id desc");
		PaginationResultVO resultVO =infoBeautyService .findListByPage(infoBeautyQuery);
		return getSuccessResponseVO(resultVO);
	}



	@RequestMapping("saveBeautAccount")
	@GlobalInterceptor(checkadmin = true)
	public ResponseVO saveBeautAccount(InfoBeauty infoBeauty) {
		this.infoBeautyService .saveAccount(infoBeauty);
		return getSuccessResponseVO(null);
	}

	@RequestMapping("delBeautAccount")
	@GlobalInterceptor(checkadmin = true)
	public ResponseVO delBeautAccount(@NotNull Integer beautyId) {
		this.infoBeautyService.deleteById(beautyId);
		return getSuccessResponseVO(null);
	}

}
