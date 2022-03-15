package com.onelive.common.exception;

import cn.hutool.core.util.StrUtil;
import com.onelive.common.config.aspect.ReqLogHandler;
import com.onelive.common.constants.other.HeaderConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.utils.Login.IPAddressUtil;
import com.onelive.common.utils.Login.LoginInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName : ExceptionHandle
 * @Description : 全局异常捕捉
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public ResultInfo<Object> handle(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error(request.getRequestURI() + ",全局异常Exception：{}", handleLog(request), e);
        //log.error(request.getRequestURI() + ",全局异常Exception", e);
        response.setHeader(HeaderConstants.ONELIVEAPPLETYPE, LoginInfoUtil.getKeyType());
        return ResultInfo.error(StatusCode.SYSTEM_ERROR_2.getCode(),StatusCode.SYSTEM_ERROR_2.getMsg()); //TODO 正式环境去掉异常信息
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResultInfo<Object> handle(MaxUploadSizeExceededException e, HttpServletRequest request, HttpServletResponse response) {
        log.error(request.getRequestURI() + ", MaxUploadSizeExceededException:{}", e.getMessage(), e);
        response.setHeader(HeaderConstants.ONELIVEAPPLETYPE, LoginInfoUtil.getKeyType());
        return ResultInfo.getInstance(StatusCode.BUSINESS_ERROR.getCode(), "上传文件过大，请重新上传");
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResultInfo<Object> businessException(BusinessException e, HttpServletRequest request, HttpServletResponse response) {
        log.error(request.getRequestURI() + ", BusinessException:{}", handleLog(request), e);
        response.setHeader(HeaderConstants.ONELIVEAPPLETYPE, LoginInfoUtil.getKeyType());
        if(e.getStatusCode()!=null){
            return ResultInfo.getInstance(e.getStatusCode());
        }
        return ResultInfo.getInstance(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultInfo<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request, HttpServletResponse response) {
        log.error(request.getRequestURI() + ",不支持当前请求方法", e);
        response.setHeader(HeaderConstants.ONELIVEAPPLETYPE, LoginInfoUtil.getKeyType());
        return ResultInfo.getInstance(HttpStatus.METHOD_NOT_ALLOWED.value(), "不支持当前请求方法");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultInfo<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request, HttpServletResponse response) {
        log.error(request.getRequestURI() + ",参数解析失败：{}", handleLog(request), e);
        response.setHeader(HeaderConstants.ONELIVEAPPLETYPE, LoginInfoUtil.getKeyType());
        return ResultInfo.getInstance(StatusCode.UNLEGAL);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultInfo<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request, HttpServletResponse response) {
        log.error(request.getRequestURI() + ",参数解析失败：{}", handleLog(request), e);
        response.setHeader(HeaderConstants.ONELIVEAPPLETYPE, LoginInfoUtil.getKeyType());
        return ResultInfo.getInstance(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    private String handleLog(final HttpServletRequest request) {
        String str = null;
        try {
            //请求ip
            String ip = IPAddressUtil.getIpAddress(request);
            //获取请求地址
            String requestPath = request.getRequestURL().toString();
            //获取请求头
            String requestHeader = ReqLogHandler.getHeaderFromRequest(request);
            //获取请求参数
            String requestParam = ReqLogHandler.getParamFromRequest(request);
            str = "\n【请求IP】" + ip +
                    "\n【请求URL】：" + requestPath +
                    "\n【请求头】　：" + requestHeader +
                    "\n【请求参数】：" + StrUtil.cleanBlank(requestParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

}
