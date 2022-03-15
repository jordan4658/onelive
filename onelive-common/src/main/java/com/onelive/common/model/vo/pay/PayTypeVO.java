package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayTypeVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/8 11:50
 */
@Data
@ApiModel
public class PayTypeVO {

    @ApiModelProperty("支付类型id")
    private Long payTypeId;

    @ApiModelProperty("支付类型code：1-支付宝、2-微信、3-银联")
    private Integer payTypeCode;

    @ApiModelProperty("支付类型名称")
    private String payTypeName;

    @ApiModelProperty("支付类型图标url")
    private String iconUrl;

    @ApiModelProperty("是否热门")
    private Boolean isHot;

    @ApiModelProperty("支付方式信息集合")
    private List<PayWayVO> payWayVoList;
}
