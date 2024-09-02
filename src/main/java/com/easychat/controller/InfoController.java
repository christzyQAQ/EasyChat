package com.easychat.controller;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easychat.annotation.GlobalIntercepter;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.po.Info;
import com.easychat.entity.query.InfoQuery;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.entity.vo.UserInfoVO;
import com.easychat.exception.BusinessException;
import com.easychat.redis.RedisUtils;
import com.easychat.service.InfoService;
import com.easychat.utils.CopyTools;

/**
 * 用户信息Controller
 * 
 * @auther: 系统
 * @date: 2024-07-14 22:48
 */
@RestController
@RequestMapping("/userInfo/")
public class InfoController extends BaseController {

	public InfoController() {
		System.out.println("InfoController loaded");
	}

	@Resource
	private InfoService infoService;

	@Resource
	private RedisUtils redisUtils;

	@RequestMapping("loadDataList")
	public ResponseVO loadDataList(InfoQuery query) {
		return getSuccessResponseVO(infoService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("add")
	public ResponseVO add(Info bean) {
		infoService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 新增批量
	 */
	@RequestMapping("addBatch")
	public ResponseVO addBatch(@RequestBody List<Info> listBean) {
		infoService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 新增批量或修改
	 */
	@RequestMapping("addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<Info> listBean) {
		infoService.addOrUpdateBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据UserId查询
	 */
	@RequestMapping("selectByUserId")
	public ResponseVO selectByUserId(String userId) {
		return getSuccessResponseVO(infoService.selectByUserId(userId));
	}

	/**
	 * 根据UserId更新
	 */
	@RequestMapping("updateByUserId")
	public ResponseVO updateByUserId(Info bean, String userId) {
		infoService.updateByUserId(bean, userId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据UserId删除
	 */
	@RequestMapping("deleteByUserId")
	public ResponseVO deleteByUserId(String userId) {
		infoService.deleteByUserId(userId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Email查询
	 */
	@RequestMapping("selectByEmail")
	public ResponseVO selectByEmail(String email) {
		return getSuccessResponseVO(infoService.selectByEmail(email));
	}

	/**
	 * 根据Email更新
	 */
	@RequestMapping("updateByEmail")
	public ResponseVO updateByEmail(Info bean, String email) {
		infoService.updateByEmail(bean, email);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Email删除
	 */
	@RequestMapping("deleteByEmail")
	public ResponseVO deleteByEmail(String email) {
		infoService.deleteByEmail(email);
		return getSuccessResponseVO(null);
	}

	// 获取用户信息
	@RequestMapping("getUserInfo")
	@GlobalIntercepter
	public ResponseVO getUserInfo(HttpServletRequest request) {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		Info info = infoService.selectByUserId(tokenUserInfoDto.getUserId());
		UserInfoVO userInfoVO = CopyTools.copy(info, UserInfoVO.class);
		userInfoVO.setAdmin(tokenUserInfoDto.isAdmin());
		return getSuccessResponseVO(userInfoVO);
	}

	// 修改并保存用户信息
	@RequestMapping("saveUserInfo")
	@GlobalIntercepter
	public ResponseVO saveUserInfo(HttpServletRequest request, Info info, MultipartFile avatarFile,
			MultipartFile avatarCover) throws IOException {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		// 用户无法从客户端修改的信息需要置空，防止被其他人修改传入
		info.setUserId(tokenUserInfoDto.getUserId());
		info.setCreateTime(null);
		info.setEmail(null);
		info.setPassword(null);
		info.setStatus(null);
		info.setLastLoginTime(null);
		infoService.updateinfo(info, avatarFile, avatarCover);
		return getUserInfo(request);
	}

	// 修改用户密码
	@RequestMapping("updatePassword")
	@GlobalIntercepter
	//TODO @Pattern(regexp = Constants.REGEX_PASSWORD)
	public ResponseVO updatePassword(HttpServletRequest request, 
			@NotEmpty String checkCodeKey,
			@NotEmpty String checkCode, 
			@NotEmpty String oldPassword, 
			@NotEmpty  String newPassword) {
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		try {
			if (!checkCode.equalsIgnoreCase((String) redisUtils.get(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey))) {
				throw new BusinessException("图片验证码不正确");
			}
			infoService.modifyPassword(oldPassword, newPassword, tokenUserInfoDto);
			// TODO 强制退出重新登录
			return getSuccessResponseVO(null);
		} finally {
			// 验证码错误，从redis中删除验证码缓存
			redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey);// : handle finally clause
		}
	}
	
	// 退出登录
	@RequestMapping("logout")
	@GlobalIntercepter
	public ResponseVO logout(HttpServletRequest request) {
		
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo(request);
		
		//TODO 退出登录，关闭WS连接
		redisUtils.delete(Constants.REDIS_KEY_WS_TOKEN+ tokenUserInfoDto.getToken());
		redisUtils.delete(Constants.REDIS_KEY_WS_TOKEN_USERID+ tokenUserInfoDto.getUserId());
		redisUtils.delete(Constants.REDIS_KEY_WS_USER_HEART_BEAT+tokenUserInfoDto.getUserId());
		return getSuccessResponseVO("已退出登录");
	
	}
	
}