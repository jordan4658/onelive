package com.onelive.common.model.req.sys.appversion;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * app版本管理Req
 * </p>
 */
@Data
@ApiModel
public class SysAppVersionQueryReq {

	@ApiModelProperty("系统类型 1=android 2=ios")
    private Integer platformType;
}
