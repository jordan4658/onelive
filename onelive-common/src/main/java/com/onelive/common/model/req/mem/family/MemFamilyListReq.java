package com.onelive.common.model.req.mem.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 家族表req类
 * </p>
 *
 */
@Data
@ApiModel(value = " 家族数据查询请求类")
public class MemFamilyListReq {

    @ApiModelProperty("家族名")
    private String familyName;

    @ApiModelProperty("家族账号")
    private String userAccount;

    @ApiModelProperty("所属地区code")
    private String registerCountryCode;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;
}
