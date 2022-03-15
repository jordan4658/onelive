package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: TransactionTypeVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/23 11:12
 */
@Data
@ApiModel
public class TransactionTypeVO {

    @ApiModelProperty("交易类型code")
    private String code;

    @ApiModelProperty("交易类型名称")
    private String value;

    @ApiModelProperty("交易类型图标")
    private String iconUrl;
}
