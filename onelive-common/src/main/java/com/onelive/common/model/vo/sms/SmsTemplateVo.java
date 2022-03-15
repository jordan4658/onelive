package com.onelive.common.model.vo.sms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@ApiModel
public class SmsTemplateVo {

    @ApiModelProperty("短信模板ID")
    private Long id;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    private Date createTime ;

    @ApiModelProperty("最后更新人")
    private String updateUser;

    @ApiModelProperty("最后更新时间")
    private Date updateTime ;
    
    @ApiModelProperty("短信模板内容")
    private String templateContent;

    @ApiModelProperty("短信方式标识")
    private String templateCode;

    @ApiModelProperty("排序号")
    private Integer sortNum = 0;

    @ApiModelProperty("是否开启：true-是、false-否")
    private Boolean isOpen ;

    @ApiModelProperty("是否删除：true-是、false-否")
    private Boolean isDelete ;


}
