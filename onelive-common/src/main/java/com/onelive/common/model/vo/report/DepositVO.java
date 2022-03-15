package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName DepositVO
 * @Desc 入款报表VO类
 * @Date 2021/4/23 9:49
 */
@Data
@ApiModel
public class DepositVO {

    @ApiModelProperty("入款类型 提示：前端只展示，不做任何处理")
    private String depositType;
    @ApiModelProperty("人数 提示：前端只展示，不做任何处理")
    private String peopleNum;
    @ApiModelProperty("成功次数 提示：前端只展示，不做任何处理")
    private String successNum;
    @ApiModelProperty("失败次数 提示：前端只展示，不做任何处理")
    private String failNum;
    @ApiModelProperty("成功率 提示：前端只展示，不做任何处理")
    private String successRate;
    @ApiModelProperty("入款总额 提示：前端只展示，不做任何处理")
    private String depositAmount;
}    
    