package com.onelive.common.model.vo.mem;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class MemUserLevelVO {

    private Long id;

    /**
     * 等级权重
     */
    @ApiModelProperty("等级")
    private Integer levelWeight;

    /**
     * 等级名称
     */
    @ApiModelProperty("等级名称")
    private String levelName;

    /**
     * 等级图标
     */
    @ApiModelProperty("等级图标")
    private String levelIcon;


    /**
     * 晋升条件-充值
     */
    @ApiModelProperty("晋升条件")
    private BigDecimal promotionRecharge;

    /**
     * 昵称颜色
     */
//    @ApiModelProperty("昵称颜色")
//    private String nameColor;

    /**
     * 聊天框底色
     */
//    @ApiModelProperty("聊天框底色")
//    private String chatColor;

    /**
     * 直播弹幕颜色
     */
//    @ApiModelProperty("直播弹幕颜色")
//    private String barrageColor;


    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
