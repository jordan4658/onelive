package com.onelive.common.model.req.mem.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 主播查询接口参数
 */
@Data
@ApiModel("主播查询接口参数")
public class MemUserAnchorSearchReq {

    @ApiModelProperty("用户登录账号/主播账号")
    private String userAccount;

}
