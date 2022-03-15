package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemUserGroupSelectVO {

    @ApiModelProperty("层级ID")
    private Long userGroupId;

    @ApiModelProperty("层级名称")
    private String groupName;


}
