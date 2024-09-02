package com.easychat.exception;

import com.easychat.enums.ResponseCodeEnum;

public class BusinessException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7679327726437806882L;
	private ResponseCodeEnum codeEnum;
    private Integer code;
    private String msg;

    public BusinessException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BusinessException(Throwable e) {
        super(e);
    }

    public BusinessException(ResponseCodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.codeEnum = codeEnum;
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }


    public ResponseCodeEnum getCodeEnum() {
        return codeEnum;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 重写fillInStackTrace 业务异常不需要堆栈信息，提高效率。
     *
     * @return
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
