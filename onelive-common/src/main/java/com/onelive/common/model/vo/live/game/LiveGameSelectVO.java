package com.onelive.common.model.vo.live.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("直播分类游戏选项分类内容")
public class LiveGameSelectVO {

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类ID")
    private Integer categoryId;

    @ApiModelProperty("游戏列表")
    private List<LiveGameItemSelectVO> gameList;

}
