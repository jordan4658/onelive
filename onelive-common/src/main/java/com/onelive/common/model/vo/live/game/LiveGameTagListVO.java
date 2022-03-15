package com.onelive.common.model.vo.live.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播游戏标签列表实体类")
public class LiveGameTagListVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 类型名称
     */
    @ApiModelProperty("类型名称")
    private String name;

    @ApiModelProperty("标签唯一标识")
    private String code;
    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("是否显示 false否 true是")
    private Boolean isShow;
}
