package com.onelive.common.model.vo.sms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class SeeSmsVo {

    @ApiModelProperty("短信方式ID")
    private Long  id;

    @ApiModelProperty("短信方式名称")
    private String  smsName;

    @ApiModelProperty("apikey(或者账号)")
    private String apiKey="";

    @ApiModelProperty("秘钥(或者密码)")
    private String secretKey="";

    @ApiModelProperty("发送短信的UrL")
    private String sendUrl;

    @ApiModelProperty("查询短信余额url")
    private String queryUrl;

    @ApiModelProperty("短信发送有效时间")
    private Integer validTime;

    @ApiModelProperty("短信方式标识")
    private String smsCode;

    @ApiModelProperty("排序号")
    private Integer sortNum;

    @ApiModelProperty("是否开启：true-是、false-否")
    private Boolean isOpen=true;

    @ApiModelProperty("是否删除：true-是、false-否")
    private Boolean isDelete=false;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("最后创建人")
    private String updateUser;

    @ApiModelProperty("最后更新时间")
    private Date updateTime;


}
