package com.easychat.controller;
import java.io.File;
import java.io.IOException;

/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 26, 202410:33:48 AM
* @ClassName:AdminGroupController.java

*/
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.redis.core.RedisConnectionUtils.RedisConnectionProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.redis.RedisComponent;
import com.easychat.annotation.GlobalIntercepter;
import com.easychat.dto.SysSettingDto;
import com.easychat.entity.config.AppConfig;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.service.GroupInfoService;


@RestController("adminSetingController")
@RequestMapping("/admin/")
public class AdminSetingController extends BaseController {

	@Resource
	private GroupInfoService groupInfoService; 
	
	@Resource
	private RedisComponent redisComponent;
	
	@Resource
	private AppConfig appConfig;
	
	@RequestMapping("getSysSetting")
	@GlobalIntercepter(checkadmin = true)
	public ResponseVO getSysSetting() {		
		SysSettingDto sysSettingDto=redisComponent.getSysSetting();		
		return getSuccessResponseVO(sysSettingDto);				
	}
	
	@RequestMapping("saveSysSetting")
	@GlobalIntercepter(checkadmin = true)
	public ResponseVO saveSysSetting(SysSettingDto sysSettingDto,
									MultipartFile robotFile,
									MultipartFile robotCover) throws IOException {	
		if(robotFile!=null) {
			String baseFolder = appConfig.getProjectFloder()+Constants.FILE_FOLDER_FILE;
			File targeFileFolder =new File(baseFolder+Constants.FILE_FOLDER_AVATAR_NAME);
			if(!targeFileFolder.exists()) {
				targeFileFolder.mkdir();
			}
			String filePath =targeFileFolder.getPath()+"/"+Constants.ROBOT_UID+Constants.IMAGE_SUFFIX;
			robotFile.transferTo(new File (filePath));
			robotCover.transferTo(new File (filePath+Constants.COVER_IMAGE_SUFFIX));
		}
		redisComponent.saveSysSetting(sysSettingDto);		
		return getSuccessResponseVO(sysSettingDto);				
	}


	
	
	
	
}
