package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName FundsReportVO
 * @Desc TODO
 * @Date 2021/4/22 16:03
 */
@Data
@ApiModel
public class FundsReportVO {

    @ApiModelProperty("列表数据")
    private List<FundsReportListVO> list;
    @ApiModelProperty("报表汇总")
    private FundsReportSummaryVO summary;


}    
    