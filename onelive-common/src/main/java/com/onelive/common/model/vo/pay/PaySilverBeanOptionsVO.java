package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class PaySilverBeanOptionsVO {

    @ApiModelProperty("提现快捷选项ID")
    private Long silverBeanOptionsId;

    @ApiModelProperty("多个值使用逗号分割开")
    private String optionsContent;

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
