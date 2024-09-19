package com.easychat.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easychat.annotation.GlobalInterceptor;
import com.easychat.dto.MessageSendDto;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.po.Info;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.entity.vo.UserInfoVO;
import com.easychat.exception.BusinessException;
import com.easychat.redis.RedisComponent;
import com.easychat.redis.RedisUtils;
import com.easychat.service.ContactApplyService;
import com.easychat.service.InfoService;
import com.easychat.utils.CopyTools;
import com.easychat.websocket.MessageHandler;
import com.wf.captcha.ArithmeticCaptcha;


/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:2024年7月14日下午10:55:35
* @ClassName:AccountController.java

*/
@RestController("AccountController")
@RequestMapping("/account")
@Validated
public class AccountController extends BaseController{
	private static final Logger logger=LoggerFactory.getLogger(AccountController.class);

	@Resource
    private  InfoService infoService;
	@Resource
	private  RedisUtils<String> redisUtils  ;   
	
	@Resource
	private RedisComponent redisComponent;
	
	@Resource 
	private ContactApplyService contactApplyService;
	
	@Resource
	private MessageHandler messageHandler;
//    @Autowired
//    public AccountController(@Qualifier("infoService")InfoService infoService) {
//    	this.infoService = infoService;
//    }
 
    

    
	@RequestMapping("/checkCode")
	public ResponseVO checkcode(){	
		redisUtils.logRedisTemplateConfiguration();
		redisUtils.init();
		ArithmeticCaptcha captcha =new ArithmeticCaptcha(100,42);
		String code =captcha.text();
		String checkCodeKey= UUID.randomUUID().toString();	
		//设置验证码过期时间 10min
		redisUtils.setex(Constants.REDIS_KEY_CHECK_CODE+checkCodeKey,code,Constants.REDIS_TIME_1MIN *10);	
		String checkCodeBase64=captcha.toBase64();
		// 在控制台输出 checkCodeKey 和 checkCode
	    System.out.println("checkCodeKey: " + checkCodeKey);
	    System.out.println("checkCode: " + code);
		Map<String,String> result =new HashMap<>();
		result.put("checkCode", checkCodeBase64);
		result.put("checkCodeKey", checkCodeKey);
		return getSuccessResponseVO(result);
	}
	
	
	@RequestMapping("/register")
	// TODO @Pattern(regexp = Constants.REGEX_PASSWORD)
	public ResponseVO<?> register(@NotEmpty String checkCodeKey,
								@NotEmpty @Email  String email,
								@NotEmpty  String password,
								@NotEmpty String nickname,
								@NotEmpty String checkCode ){	
		try {
			if(!checkCode.equalsIgnoreCase((String )redisUtils.get(Constants.REDIS_KEY_CHECK_CODE +checkCodeKey))) {
				throw new BusinessException("图片验证码不正确");
			}			
			infoService.register(email, nickname, password);			
			return getSuccessResponseVO(null);
		} finally {
			//验证码错误，从redis中删除验证码缓存
			redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE +checkCodeKey);// : handle finally clause
		}
	}
	
	
	@RequestMapping("/login")
	public ResponseVO<?> login(	@NotEmpty String checkCodeKey,
								@NotEmpty @Email  String email,
								@NotEmpty String password,							
								@NotEmpty String checkCode ){	
		try {
			if(!checkCode.equalsIgnoreCase((String )redisUtils.get(Constants.REDIS_KEY_CHECK_CODE +checkCodeKey))) {
				throw new BusinessException("图片验证码不正确");
			}
			
			TokenUserInfoDto tokenUserInfoDto = infoService.login(email,  password);
			
			Info info =infoService.selectByUserId(tokenUserInfoDto.getUserId());
			UserInfoVO userInfoVO=CopyTools.copy(info, UserInfoVO.class);
			
			userInfoVO.setToken(tokenUserInfoDto.getToken());
			userInfoVO.setAdmin(tokenUserInfoDto.isAdmin());
			String userId=tokenUserInfoDto.getUserId();
			
			return getSuccessResponseVO(userInfoVO);
		} finally {
			//验证码错误，从redis中删除验证码缓存
			redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE +checkCodeKey);
		}
	}
	
	@GlobalInterceptor
	@RequestMapping("/getSysSetting")
	public ResponseVO getSysSetting( ){
				
		return getSuccessResponseVO(redisComponent.getSysSetting());	
				
	}
	
	@RequestMapping("/test")
	public ResponseVO test( ){
			MessageSendDto sendDto =new MessageSendDto();	
			sendDto.setMessageContent("hahha"+System.currentTimeMillis());
			messageHandler.sendMessage(sendDto);
		return getSuccessResponseVO(null);	
				
	}
		

}
