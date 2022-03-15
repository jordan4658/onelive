package com.onelive.common.model.vo.mem;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mao	
 *	金币兑换银豆传输对象
 */
@Data
@ApiModel
public class ChangeSilverBeanVo {

    @ApiModelProperty("兑换成银豆的金币数量 [必填]")
    private BigDecimal goldNum;

}
