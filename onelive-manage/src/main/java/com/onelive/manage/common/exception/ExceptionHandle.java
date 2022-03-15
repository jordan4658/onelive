//package com.onelive.manage.common.exception;
//
//
//import com.onelive.common.exception.BusinessException;
//import com.onelive.common.model.common.ResultInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @ClassName : ExceptionHandle
// * @Description : 全局异常捕捉
// * @Date 2021/3/15 10:17
// */
//@RestControllerAdvice
//@Slf4j
//public class ExceptionHandle {
//    @Resource
//    private HttpServletRequest request;
//
//    @ExceptionHandler(value = Exception.class)
//    public ResultInfo handle(Exception e) {
//        log.error(request.getRequestURI() + ",Exception:{}", e.getMessage(), e);
//        return  ResultInfo.error(e.getMessage()); //TODO 正式环境去掉异常信息
//    }
//
//    @ExceptionHandler(value = BusinessException.class)
//    public ResultInfo BusinessException(BusinessException e) {
//        log.error(request.getRequestURI() + ", BusinessException:{}", e.getMessage(), e);
//        return ResultInfo.getInstance(e.getCode(), e.getMessage());
//    }
//
//}
