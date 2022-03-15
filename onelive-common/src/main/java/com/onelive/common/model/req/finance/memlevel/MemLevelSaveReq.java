package com.onelive.common.model.req.finance.memlevel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * MemLevelSaveReq
 *
 * @author kevin
 * @version 1.0.0
 * @since 2021年10月27日 下午7:16:10
 */
@Data
@ApiModel("会员层级保存参数对象")
public class MemLevelSaveReq {

    /**
     * 层级名称
     */
    @NotEmpty(message = "层级名称不能为空")
    @ApiModelProperty(value = "层级名称", required = true, example = "张三")
    private String name;

    /**
     * 入款次数限制
     */
    @ApiModelProperty(value = "入款次数限制", required = true, example = "1000")
    private Long depositTimesLimit;

    /**
     * 单次入款金额限制
     */
    @ApiModelProperty(value = "单次入款金额限制", required = true, example = "10000.00")
    private BigDecimal singleDepositLimit;

    /**
     * 总入款金额限制
     */
    @ApiModelProperty(value = "总入款金额限制", required = true, example = "10000.00")
    private BigDecimal totalDepositLimit;

    /**
     * 最大出款次数限制
     */
    @ApiModelProperty(value = "最大出款次数限制", required = true, example = "1000")
    private Long withdrawalTimesLimit;

    /**
     * 出款总额限制
     */
    @ApiModelProperty(value = "最大出款次数限制", required = true, example = "1000")
    private BigDecimal totalWithdrawalLimit;

    /**
     * 有效开始时间
     */
    @NotNull(message = "有效开始时间不能为空")
    @ApiModelProperty(value = "有效开始时间", required = true, example = "2021-08-08")
    private Date efficientStartTime;

    /**
     * 有效结束时间
     */
    @NotNull(message = "有效结束时间不能为空")
    @ApiModelProperty(value = "有效结束时间", required = true, example = "2021-09-08")
    private Date efficientEndTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", required = false, example = "2021-09-08")
    private String remark;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "备注", required = true, example = "2021-09-08")
    private Integer sort;
}
