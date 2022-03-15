package com.onelive.common.model.vo.live;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "粉丝列表用户传输类")
public class FansUserVO {


    @ApiModelProperty("会员id")
    private String userId;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String avatar;
    
    @ApiModelProperty("性别 0保密 1男 2女")
    private Integer sex;
    
    @ApiModelProperty("关注时间")
    private String createTime;

    @ApiModelProperty("等级")
    private Integer userLevel;
    
    @ApiModelProperty("用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长")
    private Integer userType;

    @ApiModelProperty("用户个性签名")
    private String personalSignature;

    public String getAvatar() {
        return AWSS3Util.getAbsoluteUrl(avatar);
    }
}
