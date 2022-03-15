package com.onelive.common.model.req.mem;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FamilyFinancialRecordReq implements Serializable {

	private static final long serialVersionUID = 1L;

    @ApiModelProperty("账变类型 1：转入 2： 提出 3：其他 ")
    private Integer type;
    
    @ApiModelProperty(value = "第几页", required = false, example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页最大数量", required = false, example = "10")
    private Integer pageSize = 10;

}
