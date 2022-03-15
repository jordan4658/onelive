package com.onelive.common.model.req.mem;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息修改请求参数
 */
@Data
@ApiModel
public class MemUserInfoEditReq implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户头像（相对路径）（用户与主播共用）
     */
    @ApiModelProperty(value = "用户头像[必填]",required = true)
    private String avatar;

    /**
     * 性别 0保密 1男 2女（用户与主播共用）
     */
    @ApiModelProperty(value = "性别 0保密 1男 2女[必填]",required = true)
    private Integer sex;

    /**
     * 生日日期
     */
    @ApiModelProperty(value = "生日日期[必填]",required = true)
    private Date birthday;

    /**
     * 家乡
     */
    @ApiModelProperty(value = "家乡[必填]",required = true)
    private String hometown;

    /**
     * 感情状态 0保密 1单身 2恋爱 3已婚
     */
    @ApiModelProperty(value = "感情状态 0保密 1单身 2恋爱 3已婚[必填]",required = true)
    private Integer maritalStatus;

    /**
     * 职业代码, 关联职业表
     */
    @ApiModelProperty(value = "职业代码[必填]",required = true)
    private String occupationCode;


    public void setAvatar(String avatar) {
        this.avatar = AWSS3Util.getRelativeUrl(avatar);
    }
}
