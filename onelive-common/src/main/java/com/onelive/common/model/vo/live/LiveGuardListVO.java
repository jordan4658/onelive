package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author harden
 * @Description: 直播间守护榜列表
 * @date 2021/4/5
 */
@Data
@ApiModel
public class LiveGuardListVO {

    @ApiModelProperty("排名")
    private Integer rankNo;

    @ApiModelProperty("会员id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("等级昵称")
    private String levelName;

    @ApiModelProperty("等级图标")
    private String levelIcon;

    @ApiModelProperty("贡献火力,计算方式：在直播间送出的礼物总金币数 * 100")
    private String firepower= "0";




}
