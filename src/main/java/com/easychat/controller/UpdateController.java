package com.easychat.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easychat.annotation.GlobalIntercepter;
import com.easychat.dto.SysSettingDto;
import com.easychat.entity.config.AppConfig;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.po.Update;
import com.easychat.entity.query.UpdateQuery;
import com.easychat.entity.vo.PaginationResultVO;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.entity.vo.UpdateVO;
import com.easychat.enums.AppUpdateFileTypeEnum;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.mappers.UpdateMapper;
import com.easychat.redis.RedisComponent;
import com.easychat.service.GroupInfoService;
import com.easychat.service.UpdateService;
import com.easychat.utils.CopyTools;
import com.easychat.utils.StringTools;

/**
 * app发布Controller
 * 
 * @auther: 系统
 * @date: 2024-07-26 21:37
 */
@RestController("updateController")
@RequestMapping("/update/")
public class UpdateController extends BaseController {

	@Resource
	private UpdateService updateService;
	@Resource
	private UpdateMapper updateMapper;
	@Resource
	private AppConfig appConfig;

	@RequestMapping("checkVersion")
	public ResponseVO loadUpdateList(String version, String uid) {
		if (StringTools.isEmpty(version)) {
			return getSuccessResponseVO(null);				
		}
		Update update= updateMapper.selectLatestUpdate(version, uid);
		if (update == null) {
			return getSuccessResponseVO(null);		
		}
		UpdateVO updateVO = CopyTools.copy(update, UpdateVO.class);
		if(update.getFileType().equals(AppUpdateFileTypeEnum.LOCAL.getType())){
			File file = new File(appConfig.getProjectFloder()+Constants.APP_UPDATE_FOLDER+Constants.APP_EXE_SUFFIX);
			updateVO.setSize(file.length());
		}else{
			updateVO.setSize(0L);
		}

		updateVO.setUpdateList(Arrays.asList(update.getUpdateDescArray()));
		String fileName =Constants.APP_NAME+update.getVersion()+Constants.APP_EXE_SUFFIX;
		updateVO.setFlieName(fileName);
		return getSuccessResponseVO(updateVO);

	}

		
	}


