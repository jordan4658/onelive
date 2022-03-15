package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播列表选择列表实体类")
public class LiveStudioSelectVO {

    @ApiModelProperty("直播间标题")
    private String studioTitle;

    @ApiModelProperty("房间号")
    private String studioNum;
}
