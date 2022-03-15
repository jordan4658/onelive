package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户中心查询活动列表实体类
 */
@Data
@ApiModel
public class SysActivityInfoVO {

    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String imgUrl;

    /**
     * 跳转模块
     */
    @ApiModelProperty("跳转模块")
    private String skipModel;

    /**
     * 跳转路径
     */
    @ApiModelProperty("跳转路径")
    private String skipUrl;

}
