package com.onelive.common.model.req.live;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LiveRecommendByCodeListReq  implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("对应前端页签code[必填]")
    private String code ;

    @ApiModelProperty("上次返回的最后id")
    private String nextId;
    
    @ApiModelProperty("地区")
    private String country;


}
