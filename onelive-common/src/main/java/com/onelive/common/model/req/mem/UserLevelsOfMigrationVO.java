package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserLevelsOfMigrationVO {

    @ApiModelProperty(value = "当前层级ID[必填]",required = true)
    private Long SourceUserGroupId;

    @ApiModelProperty(value = "目标层级ID[必填]",required = true)
    private Long targetUserGroupId;

}
