package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间在线人数列表")
public class LiveOnlineListVO {

    @ApiModelProperty("会员id")
    private Long userId;

    @ApiModelProperty("会员头像")
    private String avatar;

    @ApiModelProperty("等级")
    private Integer level;

    @ApiModelProperty("等级昵称")
    private String levelName;

    @ApiModelProperty("等级图标")
    private String levelIcon;

}
