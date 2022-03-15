package com.onelive.common.model.req.mem;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class MemUserGroupManageUpReq {


    @ApiModelProperty("层级ID")
    private Integer userGroupId;

    @ApiModelProperty("用户ID集合")
    private List<Long> userIds;

}
