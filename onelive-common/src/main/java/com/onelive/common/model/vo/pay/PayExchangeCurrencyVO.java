package com.onelive.common.model.vo.pay;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class PayExchangeCurrencyVO {

    @ApiModelProperty("汇率ID")
    private Long exchangeCurrencyId;

    @ApiModelProperty("汇率国家代码")
    private String currencyCode;

    @ApiModelProperty("转换前的货币代码")
    private String currencyF;

    @ApiModelProperty("转换前的货币名称")
    private String currencyFName;

    @ApiModelProperty("转换成的货币代码")
    private String currencyT;

    @ApiModelProperty("转换成的货币名称")
    private String currencyTName;

    @ApiModelProperty("转换金额")
    private String currencyFD;

    @ApiModelProperty("当前汇率（其他货币兑换美金的汇率）")
    private String exchange;

    @ApiModelProperty("提现汇率：比当前汇率高（默认为当前汇率）")
    private String txExchange;

    @ApiModelProperty("充值汇率：比当前汇率低（默认为当前汇率）")
    private String czExchange;

    @ApiModelProperty("兑换的币种单位标识")
    private String currencyUnit;

    @ApiModelProperty("是否删除")
    private Boolean isDelete;

    @ApiModelProperty("查询时间")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    private Date createdTime;
}
