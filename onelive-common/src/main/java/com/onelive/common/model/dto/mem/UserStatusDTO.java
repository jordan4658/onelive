package com.onelive.common.model.dto.mem;

import lombok.Data;

/**
 * @ClassName UserStatusDTO
 * @Desc app与pc端的用户状态
 * @Date 2021/3/16 10:26
 */
@Data
public class UserStatusDTO {
    /**
     * 是否冻结 0否1是
     */
    private Boolean isFrozen = false;

    /**
     * 是否返点 0否1是
     */
    private Boolean isCommission = true;

    /**
     * 是否允许投注 0否1是
     */
    private Boolean isBet = true;

    /**
     * 是否允许出款 0否1是
     */
    private Boolean isDispensing = true;

    /**
     * 是否是直播超级管理员 0否1是
     */
    private Boolean isSuperLiveManage = false;

    /**
     * 当前用户所在国家id
     */
    private Long countryId;


}
    