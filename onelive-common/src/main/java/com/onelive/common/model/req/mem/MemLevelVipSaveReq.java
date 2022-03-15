package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class MemLevelVipSaveReq implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;

    /**
     * 等级权重
     */
    @ApiModelProperty(value = "等级[必填]",required = true)
    private Integer levelWeight;

    /**
     * 等级名称
     */
    @ApiModelProperty(value = "等级名称[必填]",required = true)
    private String levelName;

    /**
     * 等级图标
     */
    @ApiModelProperty(value = "等级图标[必填]",required = true)
    private String levelIcon;

    /**
     * 进场特效
     */
    @ApiModelProperty(value = "进场特效[必填]",required = true)
    private String specialEffects;

    /**
     * 晋升条件-充值
     */
    @ApiModelProperty(value = "所需充值额度[必填]",required = true)
    private BigDecimal promotionRecharge;

    /**
     * 昵称颜色
     */
    @ApiModelProperty("昵称颜色")
    private String nameColor;

    /**
     * 聊天框底色
     */
    @ApiModelProperty("聊天框底色")
    private String chatColor;

    /**
     * 直播弹幕颜色
     */
    @ApiModelProperty("弹幕颜色")
    private String barrageColor;

    /**
     * 直播发言间隔，最小为0.1（单位/秒）
     */
    @ApiModelProperty(value = "发言间隔[必填]",required = true)
    private BigDecimal vipRight;


}
