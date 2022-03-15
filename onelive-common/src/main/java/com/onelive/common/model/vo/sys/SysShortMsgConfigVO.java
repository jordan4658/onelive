package com.onelive.common.model.vo.sys;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 短信配置
 * </p>
 */
@Data
@ApiModel
public class SysShortMsgConfigVO {


	@ApiModelProperty("主键")
    private Long id;

	@ApiModelProperty("状态 true启用false禁用")
	private Boolean isFrozen;

	@ApiModelProperty(" 区号")
	private String areaCode;

	@ApiModelProperty("配置名称")
	private String configName;

	@ApiModelProperty("用户名")
	private String msgUser;

	@ApiModelProperty(" 密码")
	private String msgPwd;

	@ApiModelProperty("不同服务商的格式不一")
	private String config;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("后台创建人账号")
	private String createUser;

	@ApiModelProperty(" 更新时间")
	private Date updateTime;

	@ApiModelProperty("后台更新人账号")
	private String updateUser;

	@ApiModelProperty("商户code值，默认值为0")
	private String merchantCode;
	
	@ApiModelProperty("短信服务商配置")
	private MsgBusiness msgBusiness;

}
