package com.onelive.common.model.vo.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 栏目选项
 */
@Data
@ApiModel("栏目选项实体类")
@AllArgsConstructor
@NoArgsConstructor
public class ColumnSelectVO {
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("选项值")
    private String column;
}
