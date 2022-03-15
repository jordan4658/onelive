package com.onelive.common.model.dto.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * 国家地址数据解析类
 */
@Data
public class CountryAddrParseDTO implements Serializable {
    /**
     * 地址名称
     */
    private String name;
    /**
     * 部分没有name的地区使用这个字段
     */
    private String key;
    /**
     * 子区域地址列表,用~切割
     */
    private String sub_keys;
}
