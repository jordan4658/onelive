package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 彩票参数-迁移过来
 * @param <T>
 */
@Data
public class LotteryRequestInfo<T> {

//    @ApiModelProperty(value = "彩票签名", required = false)
//    private String apisign;
    @ApiModelProperty(value = "彩票请求数据结构[必填]", required = false)
    private T data;

//    public String getApisign() {
//        return apisign;
//    }
//
//    public void setApisign(String apisign) {
//        this.apisign = apisign;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }

//    @Override
//    public String toString() {
//        return "RequestInfo{" +
//                "apisign='" + apisign + '\'' +
//                ", data=" + data +
//                '}';
//    }
}

