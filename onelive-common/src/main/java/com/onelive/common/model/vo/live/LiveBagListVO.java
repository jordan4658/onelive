package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 背包列表查询实体类
 */
@Data
@ApiModel
public class LiveBagListVO {
    @ApiModelProperty("背包物品ID")
    private Long id;
    /**
     * 图片
     */
    @ApiModelProperty("图片")
    private String img;

    /**
     * 背包物品名称
     */
    @ApiModelProperty("背包物品名称")
    private String bagName;

    /**
     * 价格
     */
    @ApiModelProperty("价格")
    private BigDecimal price;

    /**
     * 地区名称
     */
    @ApiModelProperty("地区名称")
    private String countryNameList;

    /**
     * 状态 0启用1禁用
     */
    @ApiModelProperty("状态 0启用1禁用")
    private Boolean isFrozen;

}
