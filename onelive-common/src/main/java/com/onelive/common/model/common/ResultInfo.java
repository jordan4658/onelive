package com.onelive.common.model.common;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.onelive.common.base.LocaleMessageSourceService;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.utils.others.SpringUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "返回说明")
@Data
public class ResultInfo<T> {

    @ApiModelProperty(value = "返回状态码；200:成功 999 网络异常 401 用户被踢出或者重新登录")
    private Integer code = 200;

    @ApiModelProperty(value = "描述信息")
    private String msg;

    private T data;

    private Date timestamp = new Date();

    public ResultInfo() {
        this.msg = "";
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResultInfo<T> ok() {
        return getInstance(StatusCode.SUCCESSCODE);
    }

    public static <T> ResultInfo<T> ok(T data) {
        return getInstance(data, StatusCode.SUCCESSCODE);
    }

    public static <T> ResultInfo<T> error() {
        return getInstance(null, StatusCode.SYSTEM_ERROR);
    }

    public static <T> ResultInfo<T> error(String msg) {
        ResultInfo<T> resultInfo = getInstance(null, StatusCode.SYSTEM_ERROR);
        resultInfo.setMsg(msg);
        return resultInfo;
    }
    
    public static <T> ResultInfo<T> error(Integer code, String msg) {
    	ResultInfo<T> resultInfo = getInstance(null, StatusCode.SYSTEM_ERROR);
    	resultInfo.setMsg(msg);
    	resultInfo.setCode(code);
    	return resultInfo;
    }

    public static <T> ResultInfo<T> getInstance(StatusCode code) {
        return ResultInfo.getInstance(null, code);
    }

    public static <T> ResultInfo<T> getInstance(T data, StatusCode code) {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setCode(code.getCode());
        LocaleMessageSourceService localeMessageSourceService = SpringUtil.getBean(LocaleMessageSourceService.class);
        String msg = localeMessageSourceService.getMessage(code.name());
        resultInfo.setMsg(msg);
        if(StringUtils.isBlank(msg)){
            resultInfo.setMsg(code.getMsg());
        }
        resultInfo.setData(data);
        return resultInfo;
    }

    public static <T> ResultInfo<T> getInstance(Integer code, String info) {
        return ResultInfo.getInstance(null, code, info);
    }

    public static <T> ResultInfo<T> getInstance(T data, Integer code, String info) {
        ResultInfo<T> resultInfo = ResultInfo.getInstance();
        resultInfo.setData(data);
        resultInfo.setCode(code);
        resultInfo.setMsg(info);
        return resultInfo;
    }

    public static <T> ResultInfo<T> getInstance() {
        return new ResultInfo<>();
    }


}