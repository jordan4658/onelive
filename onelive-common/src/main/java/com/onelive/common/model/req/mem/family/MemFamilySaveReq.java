package com.onelive.common.model.req.mem.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = " 家族信息保存请求类")
public class MemFamilySaveReq {
	
	@ApiModelProperty("昵称（若不传，默认系统生成）")
    private String nickName;

	@ApiModelProperty("数据自增id")
	private Long id;

	@ApiModelProperty("关联用户表自增id")
	private Long userId;

    @ApiModelProperty("注册的手机号")
    private String mobilePhone;

    @ApiModelProperty("注册的区号")
    private String registerAreaCode;
	
    @ApiModelProperty("家族名")
    private String familyName;

    @ApiModelProperty("家族账号")
    private String userAccount;

    @ApiModelProperty("登录密码")
    private String password;

     @ApiModelProperty("账号状态")
    private Boolean isFrozen;

     @ApiModelProperty("礼物抽成比例")
    private BigDecimal giftRatio;

    @ApiModelProperty("所属地区code")
    private String registerCountryCode;

    @ApiModelProperty("备注")
    private String remark;
    
    @ApiModelProperty(value = "更新人",hidden = true)
    private String updateBy;
}
