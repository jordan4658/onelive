package com.onelive.common.model.req.live;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LiveIndexSearchReq implements Serializable{
	
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("模糊匹配:匹配accno,用户昵称 直播间标题  精确匹配：房间号， userid")
    private String context;
    
}
