package com.onelive.common.model.req.mem;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class MemUserGroupDlReq {

    @ApiModelProperty("用户层级主建IDs")
    private List<Long> userGroupIds;

}
