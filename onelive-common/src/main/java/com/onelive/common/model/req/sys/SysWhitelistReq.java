package com.onelive.common.model.req.sys;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * ip白名单
 * </p>
 */
@Data
@ApiModel
public class SysWhitelistReq {

	@ApiModelProperty("ip")
    private String ip;


}
