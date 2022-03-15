package com.onelive.common.model.vo.pay;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 汇率配置
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
@Data
@ApiModel
public class PayExchangeRateCfgVO {

    @ApiModelProperty("汇率配置ID")
    private Long exchangeRateCfgId;

    @ApiModelProperty("国家代码")
    private String currencyCode;

    @ApiModelProperty("提现汇率浮动值(百分比)，在当前汇率上加一些（默认0）")
    private String txFloatingValue;

    @ApiModelProperty("充值汇率浮动值(百分比)，在当前汇率上减一些（默认0）")
    private String czFloatingValue;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新人")
    private String updateUser;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDelete;


}
