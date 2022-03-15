package com.onelive.common.model.req.sys;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName SysCommonReq
 * @Desc 公共请求类
 * @Date 2021/4/5 19:14
 */
@Data
@ApiModel
public class SysCommonReq extends PageReq {

    @ApiModelProperty("主键id")
    private Long id;

}    
    