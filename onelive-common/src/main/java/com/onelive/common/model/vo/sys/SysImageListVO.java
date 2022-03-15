package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * VO
 * </p>
 */
@Data
@ApiModel
public class SysImageListVO {

	@ApiModelProperty("主键")
    private Long id;
	
	@ApiModelProperty("名称")
    private String name;
	
    @ApiModelProperty("说明")
   private String note;

    @ApiModelProperty("图片地址")
   private String imgUrl;
    

}
