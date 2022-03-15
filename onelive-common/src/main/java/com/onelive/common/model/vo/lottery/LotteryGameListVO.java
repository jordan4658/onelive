package com.onelive.common.model.vo.lottery;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "游戏（彩票）列表")
public class LotteryGameListVO {

    @ApiModelProperty(value = "游戏code值 关联 彩票 lotteryId 或 第三方游戏 gameCode")
    private String gameCode;

    @ApiModelProperty(value = "游戏名称")
    private String name;

    @ApiModelProperty(value = "游戏图标")
    private String iconUrl;

//    @ApiModelProperty(value = "游戏ID, 彩票关联lotteryId 第三方游戏关联 id")
//    private Long gameId;

//    @ApiModelProperty(value = "游戏类型,如棋牌chess、捕鱼fishing、电子electronic、视讯video、彩票lottery")
//    private String gameType;
//
//    @ApiModelProperty(value = "跳转地址")
//    private String jumpUrl;
//
//    @ApiModelProperty(value = "是否进行跳转")
//    private Boolean isCanJump;

    @ApiModelProperty(value = "是否是第三方游戏")
    private Boolean isThird;

//    @ApiModelProperty(value = "游戏分类ID")
//    private Integer categoryId;


    public void setIconUrl(String iconUrl) {
        this.iconUrl = AWSS3Util.getAbsoluteUrl(iconUrl);
    }
}
