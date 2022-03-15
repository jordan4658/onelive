package com.onelive.common.model.vo.agent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SearchUserVo {
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long id;
    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String userAccount;

    /**
     * 邀请码状态 1正常 0停用
     */
    @ApiModelProperty("邀请码状态 1正常 0停用")
    private Boolean status;

    /**
     * 邀请码
     */
    @ApiModelProperty("邀请码")
    private String inviteCode;

    /**
     * 是否冻结 0否1是（用户与主播共用）
     */
    @ApiModelProperty("用户是否冻结 0否1是")
    private Boolean isFrozen;

    /**
     * 用户层级id
     */
    @ApiModelProperty("用户层级")
    private String group="Not stratified";
   // private Long groupId;
}
