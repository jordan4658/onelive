package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间主播简单信息")
public class LiveAnchorVO {

    @ApiModelProperty("会员id")
    private Long userId;

    @ApiModelProperty("主播昵称")
    private String nickName;

    @ApiModelProperty("主播头像")
    private String avatar;

    @ApiModelProperty("当前用户是否已关注主播 false否true是")
    private Boolean isFocus;
    
    @ApiModelProperty("当前主播的粉丝数量")
    private Integer fansCount;
    
    @ApiModelProperty("用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长")
    private Integer userType;

}
