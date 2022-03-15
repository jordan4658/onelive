package com.onelive.common.model.vo.pay;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class PayShortcutOptionsVO {

    @ApiModelProperty("充值快捷选项ID")
    private Long shortcutOptionsId;

    @ApiModelProperty("支付方式ID")
    private Long payWayId;
    @ApiModelProperty("国家编码")
    private String countryCode;


    @ApiModelProperty("多个值使用逗号分割开")
    private String shortcutOptionsContent;

    @ApiModelProperty("是否启用：0-否，1是")
    private Boolean isEnable;

    @ApiModelProperty("是否删除：0-否，1是")
    private Boolean isDelete;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创时间")
    private Date createTime;

    @ApiModelProperty("最后更新人")
    private String updateUser;

    @ApiModelProperty("最后更新时间")
    private Date updateTime;



}
