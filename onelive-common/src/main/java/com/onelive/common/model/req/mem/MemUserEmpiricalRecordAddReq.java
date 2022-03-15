package com.onelive.common.model.req.mem;

import com.onelive.common.enums.UserEmpiricalTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class MemUserEmpiricalRecordAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 增加的经验值
     */
    @ApiModelProperty(value = "增加的经验值,请输入正数[必填]",required = true)
    private BigDecimal empiricalValue;

    /**
     * 类型 1送礼物 2玩游戏
     */
    @ApiModelProperty(value = "类型 1送礼物 2玩游戏[必填]",required = true)
    private UserEmpiricalTypeEnum type;


}
