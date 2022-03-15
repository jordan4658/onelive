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
@ApiModel("更新版本状态")
public class SysAppVersionUpdateStatusReq {

	@ApiModelProperty("主键")
    private Long id;

	@ApiModelProperty("状态 0待发布 1已发布")
    private Integer status;

}
