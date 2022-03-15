package com.onelive.common.model.vo.report;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName UserReportDetailAllVO
 * @Desc 会员报表明显展示类
 * @Date 2021/4/14 10:07
 */
@Data
@ApiModel
public class UserReportDetailAllVO {

    @ApiModelProperty("列表数据")
    private PageInfo<UserReportDetailVO> data;

    @ApiModelProperty("总金额")
    private BigDecimal sumAmount;

}    
    