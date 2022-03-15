package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * App端背包信息查询实体类
 */
@Data
@ApiModel
public class AppLiveBagVO {
	
    @ApiModelProperty("背包物品ID")
    private Long id;

    /**
     * 类型 1.座驾 2.活动礼品
     */
    @ApiModelProperty("类型 1.座驾 2.活动礼品")
    private Integer type;

    /**
     * 价格
     */
    @ApiModelProperty("价格")
    private BigDecimal price;

    /**
     * 图片
     */
    @ApiModelProperty("图片")
    private String img;

    /**
     * 动画
     */
    @ApiModelProperty("动画")
    private String animation;

    /**
     * 动画类型 1.svga动画 2.lottie动画 3.骨骼动画
     */
    @ApiModelProperty("动画类型 1.svga动画 2.lottie动画 3.骨骼动画")
    private Integer animationType;

    /**
     * 动画状态 0静态，1动态
     */
    @ApiModelProperty("动画状态 0静态，1动态")
    private Integer animationStatus;

    /**
     * 座驾停留时间(单位: 秒)
     */
    @ApiModelProperty("座驾停留时间(单位: 秒)")
    private Integer retentionTime;

}
