package com.onelive.common.model.req.mem;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FamilSearchUserAnchorReq implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("主播昵称,主播账号，查询用")
	private String nickName;
	
	@ApiModelProperty(value = "第几页", required = false, example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页最大数量", required = false, example = "10")
    private Integer pageSize = 10;
    
	private String merchantCode;

}
