package com.onelive.common.exception;

import com.onelive.common.enums.StatusCode;

/**
 *
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -3876502758804606346L;

    private Integer code;

    private StatusCode statusCode;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
        this.code = StatusCode.BUSINESS_ERROR.getCode();
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = Integer.parseInt(code);
    }

    public BusinessException(StatusCode statusCode) {
        super(statusCode.getMsg());
        this.code = statusCode.getCode();
        this.statusCode = statusCode;
    }


    public BusinessException(String message, Throwable th) {
        super(message, th);
    }

    public BusinessException(Integer code, String message, Throwable th) {
        super(message, th);
        this.code = code;
    }

    public BusinessException(Throwable th) {
        super(th);
    }

    public String getMessage() {
        return super.getMessage();
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        if (this.code == null) {
            return -1;
        }
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}