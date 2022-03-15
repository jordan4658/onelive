package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *addreq
 * </p>
 *
 */
@Data
@ApiModel
public class SysImageUpdateReq {
    
    @ApiModelProperty("主键")
   private Long id;
    
     @ApiModelProperty("名称")
    private String name;
     
     @ApiModelProperty("图片地址")
    private String imgUrl;
     
     @ApiModelProperty("说明")
    private String note;

}
