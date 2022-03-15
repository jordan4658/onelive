package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 查询用户登陆信息
 */
@Data
@ApiModel
public class MemUserLoginVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 登录ip
     */
    @ApiModelProperty("登录ip")
    private String ip;

    /**
     * 登录地区
     */
    @ApiModelProperty("登录地区")
    private String area;

    /**
     * 登录终端：app、mobile、pc
     */
    @ApiModelProperty("登录终端")
    private String loginSource;

    /**
     * 登录设备型号
     */
    @ApiModelProperty("登录设备型号")
    private String loginDevice;

    /**
     * 创建时间
     */
    @ApiModelProperty("登陆时间")
    private Date createTime;
}
