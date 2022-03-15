package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *帮助中心addreq
 * </p>
 *
 */
@Data
@ApiModel
public class SysHelpInfoListReq {

     @ApiModelProperty("标题")
    private String title;
    
     @ApiModelProperty("语言")
    private String lang;

     @ApiModelProperty("内容")
    private String content;

}
