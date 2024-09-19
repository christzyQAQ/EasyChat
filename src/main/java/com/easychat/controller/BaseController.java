package com.easychat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.easychat.dto.TokenUserInfoDto;
import com.easychat.entity.constants.Constants;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.redis.RedisUtils;

public class BaseController {

    protected static final String STATUS_SUCCESS = "success";
    protected static final String STATUS_ERROR = "error";
    
    @Resource 
    private RedisUtils redisUtils;

    protected <T> ResponseVO getSuccessResponseVO(T t) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(STATUS_SUCCESS);
        responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        responseVO.setData(t);
        return responseVO;
    }
    //从http头部请求获得token字符对应的信息
    	protected  TokenUserInfoDto  getTokenUserInfo(HttpServletRequest request) {
    	String token = request.getHeader("token");
    	TokenUserInfoDto tokenUserInfoDto =(TokenUserInfoDto) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN+token);
    	return tokenUserInfoDto;
    
    }
    
    
    
}
