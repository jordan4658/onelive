package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class LiveActVO {

    @ApiModelProperty("游戏活动列表")
    private List<LiveActListVO> gameList;

    @ApiModelProperty("直播活动列表")
    private List<LiveActListVO> liveList;

}
