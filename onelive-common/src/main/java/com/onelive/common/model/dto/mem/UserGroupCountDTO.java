package com.onelive.common.model.dto.mem;

import lombok.Data;

@Data
public class UserGroupCountDTO {

    /**
     * 用户层级ID
     */
    private Long userGroupId;

    /**
     * 用户层级-用户数量
     */
    private Long countTotal;
}
