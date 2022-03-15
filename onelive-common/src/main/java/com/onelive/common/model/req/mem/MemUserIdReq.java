package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MemUserIdReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户userId[必填]",required = true)
    private Long userId;

	@ApiModelProperty("第几页，分页用，不分页不必传")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数，分页用，不分页不必传")
	private Integer pageSize = 10;
}
