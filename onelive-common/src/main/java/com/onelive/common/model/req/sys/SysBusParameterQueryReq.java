package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lorenzo
 * @Description: 业务参数查询请求类
 * @date 2021/4/6
 */
@Data
@ApiModel
public class SysBusParameterQueryReq {

    @ApiModelProperty("业务参数父级代码")
    private String pParamCode;

    @ApiModelProperty("业务参数名称")
    private String paramName;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;

}
