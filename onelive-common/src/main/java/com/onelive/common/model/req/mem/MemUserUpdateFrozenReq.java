package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@ApiModel
public class MemUserUpdateFrozenReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID列表[必填]", required = true)
    private ArrayList<Long> userIds;

    @ApiModelProperty(value = "是否冻结, false否, true是[必填]", required = true)
    private Boolean isFrozen;
}
