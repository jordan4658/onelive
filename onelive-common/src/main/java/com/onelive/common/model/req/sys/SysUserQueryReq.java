package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 系统用户查询参数请求
 * @date 2021/4/6
 */
@Data
@ApiModel
public class SysUserQueryReq {

    @ApiModelProperty("user昵称")
    private String userName;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;

}
