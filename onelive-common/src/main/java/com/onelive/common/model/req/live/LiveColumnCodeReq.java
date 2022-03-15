package com.onelive.common.model.req.live;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播列表查询，栏目传输类")
public class LiveColumnCodeReq implements Serializable{
	
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("栏目code : 热门:hot 星秀:star 附近:nearby 游戏:game  (关注:focus 推荐:recommend 不支持)")
    private String columnCode;

    @ApiModelProperty("此房间号后面的直播间，可以为空")
    private String studioNum;
    
    @ApiModelProperty("国家唯一标识,columnCode = nearby 可以查询其他国家，'other':查询用户当前国家以外的直播间 ，null：不限制国家")
    private String countryCode;

    @ApiModelProperty("每页的条数")
    private Integer pageSize = 10;

}
