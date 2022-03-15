package com.onelive.common.model.req.sys;

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
public class SysShortMsgConfigUpdateReq {


	@ApiModelProperty("主键")
    private Long id;

	@ApiModelProperty("短信平台")
    private String msgPlatform;

	@ApiModelProperty("状态 0启用1禁用")
    private Integer isFrozen;

	@ApiModelProperty("区号")
    private String areaCode;

	@ApiModelProperty("配置名称")
    private String configName;

	@ApiModelProperty("用户名")
    private String msgUser;

	@ApiModelProperty("密码")
    private String msgPwd;

	@ApiModelProperty("主呼叫号码")
    private String mainNumber;

	@ApiModelProperty("url")
    private String url;

	@ApiModelProperty("端口")
    private String port;
}
