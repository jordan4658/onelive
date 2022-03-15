package com.onelive.common.model.req.sys.appversion;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ID参数
 */
@Data
@ApiModel
public class SysAppVersionIdReq {

    @ApiModelProperty("ID")
    private Long id;
}
