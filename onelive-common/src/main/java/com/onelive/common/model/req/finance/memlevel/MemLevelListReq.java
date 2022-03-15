package com.onelive.common.model.req.finance.memlevel;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * MemLevelListReq
 *
 * @author kevin
 * @version 1.0.0
 * @since 2021年10月27日 上午11:32:08
 */
@Data
@ApiModel("用户层级列表请求参数对象")
public class MemLevelListReq extends PageReq {

    @ApiModelProperty(value = "层级名称", required = false, example = "层级1")
    private String name;

}
