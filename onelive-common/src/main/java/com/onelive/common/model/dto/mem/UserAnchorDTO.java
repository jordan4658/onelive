package com.onelive.common.model.dto.mem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName UserAnchorDTO
 * @Desc 用户-主播信息传递类
 * @Date 2021/3/16 10:26
 */
@Data
public class UserAnchorDTO {

    @ApiModelProperty("注册的手机号")
    private String mobilePhone;

    @ApiModelProperty("注册的区号")
    private String registerAreaCode;

    /**
     * 	所属地区code(必填)
     */
    private String registerCountryCode;

    /**
     * 昵称（若不传，默认系统生成）
     */
    private String nickName;

    /**
     * 主播账号(必填)
     */
    private String userAccount;
    
    @ApiModelProperty("用户头像（相对路径）")
	private String avatar;

    /**
     * 密码，明文(必填)
     */
    private String password;
    

    /**
     * 0普通用户 1游客用户 2主播 3家族长
     */
    private Integer userType;
    

    /**
     * 是否冻结 0否1是
     */
    private Boolean isFrozen =false;

    /**
     * 备注
     */
    private String remark;
}
    