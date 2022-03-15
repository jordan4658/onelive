package com.onelive.common.model.req.live;

import java.io.Serializable;

import com.onelive.common.model.common.PageReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LivePopListReq  implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("每页最大页数")
    private Integer pageSize = 10;


}
