package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 背包物品保存请求参数
 */
@Data
@ApiModel("背包物品保存请求参数")
public class LiveBagSaveReq {

    @ApiModelProperty("更新时传入ID")
    private Long id;

    /**
     * 类型 1.座驾 2.活动礼品
     */
    @ApiModelProperty(value = "类型 1.座驾 2.活动礼品[必填]",required = true)
    private Integer type;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格[必填]",required = true)
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

    /**
     * 地区编码
     */
    @ApiModelProperty(value = "地区code列表[必填]",required = true)
    private List<String> countryCodeList;

    /**
     * 地区名称
     */
    @ApiModelProperty(value = "地区名称列表[必填]",required = true)
    private List<String> countryNameList;

    /**
     * 状态 0启用1禁用
     */
    @ApiModelProperty(value = "状态 0启用1禁用[必填]",required = true)
    private Boolean isFrozen;

    @ApiModelProperty(value = "多语言列表[必填]",required = true)
    private List<LiveBagLangSaveReq> langList;

}
