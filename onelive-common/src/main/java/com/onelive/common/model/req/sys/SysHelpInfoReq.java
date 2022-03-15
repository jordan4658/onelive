package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *帮助中心addreq
 * </p>
 *
 */
@Data
@ApiModel
public class SysHelpInfoReq {

     @ApiModelProperty("多语言数组")
    private List<SysHelpInfoListReq> data;


}
