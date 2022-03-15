package com.onelive.common.model.req.mem;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登陆信息查询请求参数
 */
@Data
@ApiModel
public class MemUserLoginReq extends PageReq {

    @ApiModelProperty(value = "会员ID[必填]",required = true)
    private String accno;

}
