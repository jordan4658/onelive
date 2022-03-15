package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemUserSelectListVO {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("会员ID")
    private String accno;

    /**
     * 用户登录账号（用户与主播共用）
     */
    @ApiModelProperty("用户账号")
    private String userAccount;

    /**
     * 昵称（用户与主播共用）
     */
    @ApiModelProperty("昵称")
    private String nickName;



}
