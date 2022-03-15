package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户等级信息
 */
@Data
@ApiModel
public class MemUserLevelInfoVO {
    @ApiModelProperty("当前等级")
    private Integer levelWeight;

    @ApiModelProperty("等级名称")
    private String levelName;

    @ApiModelProperty("等级图标")
    private String levelIcon;

    @ApiModelProperty("进度")
    private Integer progress;

    @ApiModelProperty("距离升级需要的经验值")
    private BigDecimal needExp;

    @ApiModelProperty("已有的经验值")
    private BigDecimal currentExp;

//    @ApiModelProperty("下一级所需的总经验值")
//    private BigDecimal nextLevelExp;

}
