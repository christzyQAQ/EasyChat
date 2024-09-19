package com.easychat.exception;

import com.easychat.entity.vo.ResponseVO;
import com.easychat.enums.ResponseCodeEnum;
import com.easychat.controller.BaseController;

import java.net.BindException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    Object handleException(Exception e, HttpServletRequest request) {
        log.error("请求错误，请求地址{}，错误信息：", request.getRequestURI(), e);
        ResponseVO ajaxResponse = new ResponseVO();
        if (e instanceof NoHandlerFoundException) {
            // 404
            setData(ajaxResponse, ResponseCodeEnum.CODE_404);
        } else if (e instanceof BusinessException) {
            // 业务异常
            BusinessException exception = (BusinessException) e;
            ajaxResponse.setCode(exception.getCode());
            ajaxResponse.setInfo(exception.getMsg());
        } else if (e instanceof BindException) {
            // 参数类型异常
            setData(ajaxResponse, ResponseCodeEnum.CODE_600);
        } else if (e instanceof DuplicateKeyException) {
            // 主键冲突
            setData(ajaxResponse, ResponseCodeEnum.CODE_601);
        } else {
            setData(ajaxResponse, ResponseCodeEnum.CODE_500);
        }
        ajaxResponse.setStatus(STATUS_ERROR);
        return ajaxResponse;
    }

    private void setData(ResponseVO ajaxResponse, ResponseCodeEnum codeEnum) {
        ajaxResponse.setCode(codeEnum.getCode());
        ajaxResponse.setInfo(codeEnum.getMsg());
    }

}
