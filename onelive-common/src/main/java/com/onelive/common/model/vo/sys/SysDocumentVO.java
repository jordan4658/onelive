package com.onelive.common.model.vo.sys;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * VO
 * </p>
 */
@Data
@ApiModel
public class SysDocumentVO {

	@ApiModelProperty("主键")
    private Long id;
	
	@ApiModelProperty("标题")
    private String title;
	
    @ApiModelProperty("语言")
   private String lang;

    @ApiModelProperty("内容")
   private String content;
    
    @ApiModelProperty("语种")
    private List<SysDocumentLangVO> langVOs;

}
