package com.onelive.common.model.req.pay;

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
 * 汇率查询key配置
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
@Data
@ApiModel
public class PayFindExchangeCfgAddReq {

    @ApiModelProperty("查询汇率的code（sys_parameter表中获取）")
    private String exchangeUrlCode;

    @ApiModelProperty("请求Key")
    private String exchangeKey;

//    @ApiModelProperty("每天请求频率限制（默认-1）：-1为无限制，其他数字为次数")
//    private Integer frequency = -1;


}
