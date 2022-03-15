package com.onelive.common.model.req.mem.anchor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("主播更新状态接口参数")
public class MemUserAnchorStatusReq {
    @ApiModelProperty("主播id MemUserAnchor.id")
    private Long id;

    @ApiModelProperty("是否冻结 0否1是")
    private Boolean isFrozen;

}
