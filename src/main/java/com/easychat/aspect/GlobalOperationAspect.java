package com.easychat.aspect;

import java.awt.Checkbox;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.Joinpoint;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.easychat.annotation.GlobalIntercepter;
import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.exception.BusinessException;
import com.easychat.exception.GlobalExceptionHandler;
import com.easychat.redis.RedisUtils;
import com.easychat.utils.StringTools;

import org.aspectj.lang.JoinPoint;


/*
* @Author Zy 1193263034@qq.com
* @version 1.0.0
* @CreatTime:Jul 21, 20245:45:27 PM
* @ClassName:GlobalOperationAspect.java

*/

@Aspect
@Component("globalOperationAspect")
public class GlobalOperationAspect {

	@Resource 
	private RedisUtils redisUtils;
	private static final Logger logger= LoggerFactory.getLogger(GlobalOperationAspect.class);
	
	@Before("@annotation(com.easychat.annotation.GlobalIntercepter)")
	public void IntercepterDo(JoinPoint point) {
		Method method = ((MethodSignature) point.getSignature()).getMethod();
		GlobalIntercepter intercepter=method.getAnnotation(GlobalIntercepter.class);
		if (intercepter==null) {
			return;
		}
		if (intercepter.checkLogin()||intercepter.checkadmin()) {
			CheckLogin(intercepter.checkadmin());
			
		}
	}
	
	private void CheckLogin(boolean checkAdmin ) {
		
		try {
			HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			String token =request.getHeader("token");
			
			if (StringTools.isEmpty(token)) {
				throw new BusinessException(ResponseCodeEnum.CODE_901);
			}
			TokenUserInfoDto tokenUserInfoDto=(TokenUserInfoDto) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN+token);

			
			if (checkAdmin&&!tokenUserInfoDto.isAdmin()) {
				throw new BusinessException(ResponseCodeEnum.CODE_404);
			}
		} catch (BusinessException e) {		
			logger.error("全局拦截异常");
			throw e;
		}catch (Exception e) {
			logger.error("全局拦截异常");
			throw new BusinessException(ResponseCodeEnum.CODE_500);
		}catch (Throwable e) {
			logger.error("全局拦截异常");
			throw new BusinessException(ResponseCodeEnum.CODE_500);
		}
		

		
	}
}
