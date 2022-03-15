package com.onelive.common.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName PageReq
 * @Desc 分页参数
 * @Date 2021/4/14 19:52
 */
@Data
@ApiModel("分页请求参数")
public class PageReq {

    @ApiModelProperty(value = "第几页", required = false, example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页最大数量", required = false, example = "10")
    private Integer pageSize = 10;

}
    