package com.onelive.common.model.dto.mem;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @ClassName UserInfoUpdateDTO
 * @Desc 更新用户基本信息
 * @Date 2021/4/12 10:27
 */
@Data
@ApiModel
public class UserInfoUpdateDTO {
    /**
     * 用户id
     */
    private Long uid;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户性别,1男 2女 3保密
     */
    private Integer sex;
    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 会员真实姓名
     */
    private String realName;

}    
    