package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播间游戏标签列表实体类")
public class LiveGameTagListVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 类型名称
     */
    @ApiModelProperty("标签名称")
    private String name;
}
