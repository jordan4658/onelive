package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Tab栏配置内容")
public class LiveTabItemVo {
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 选中的图标
     */
    @ApiModelProperty("选中的图标")
    private String iconSelectedUrl;

    /**
     * 未选中的图标
     */
    @ApiModelProperty("未选中的图标")
    private String iconUnselectedUrl;
    /**
     * 唯一标识: live直播 game游戏 recharge充值 my我的
     */
    @ApiModelProperty("唯一标识: live直播 game游戏 recharge充值 my我的")
    private String iconCode;
}
