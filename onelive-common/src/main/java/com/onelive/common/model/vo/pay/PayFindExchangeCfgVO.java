package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class PayFindExchangeCfgVO {

    @ApiModelProperty("汇率查询key ID")
    private Long exchangeKeyId;

    @ApiModelProperty("查询汇率的code（sys_parameter表中获取）")
    private String exchangeUrlCode;

    @ApiModelProperty("查询汇率url")
    private String exchangeUrl;

    @ApiModelProperty("请求Key")
    private String exchangeKey;

    @ApiModelProperty("每天请求频率限制（默认-1）：-1为无限制，其他数字为次数")
    private Integer frequency;

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
