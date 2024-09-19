package com.easychat.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.entity.po.Update;
import com.easychat.entity.query.UpdateQuery;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.service.UpdateService;

/**
 * app发布Controller
 * 
 * @auther: 系统
 * @date: 2024-07-26 21:37
 */
@RestController
@RequestMapping("/admin/")
public class AdminUpdateController extends BaseController {

	@Resource
	private UpdateService updateService;

	@RequestMapping("loadUpdateList")
	@GlobalInterceptor(checkadmin = true)
	public ResponseVO loadUpdateList(UpdateQuery updateQuery) {
		updateQuery.setOrderBy("id desc");
		PaginationResultVO resultVO=this.updateService.findListByPage(updateQuery);
		return getSuccessResponseVO(resultVO);
	}

	@RequestMapping("saveUpdate")
	@GlobalInterceptor(checkadmin = true)
	public ResponseVO saveUpdate(Integer id, @NotEmpty String version, @NotEmpty String updateDesc,
			@NotNull Integer fileType, String outerLink,MultipartFile file) throws IOException {
		Update appUpdate =new Update();
		appUpdate.setId(id);
		appUpdate.setVersion(version);
		appUpdate.setUpdateDesc(updateDesc);
		appUpdate.setFileType(fileType);
		appUpdate.setOuterLink(outerLink);		
		updateService.saveUpdate(appUpdate, file);		
		return getSuccessResponseVO(appUpdate);
	}
	
	@RequestMapping("delUpdate")
	@GlobalInterceptor(checkadmin = true)
	public ResponseVO delUpdate(@NotNull Integer id)  {
		updateService.deleteById(id);		
		return getSuccessResponseVO(null);
	}
	
	@RequestMapping("postUpdate")
	@GlobalInterceptor(checkadmin = true)
	public ResponseVO postUpdate(@NotNull Integer id,
			@NotNull Integer status,
			String grayScaleUid) {
		Update appUpdate =updateService.selectById(id);
		if(appUpdate==null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}		
		updateService.postUpdate(id, status, grayScaleUid);		
		return getSuccessResponseVO(null);
	}

	


}