package com.onelive.common.model.req.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName UserReportReq
 * @Desc 用户报表请求接口
 * @Date 2021/4/13 16:34
 */
@Data
@ApiModel
public class UserReportReq {

    @ApiModelProperty("会员账号")
    private String account;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;


}    
    