package com.onelive.common.model.req.mem.usercenter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询活动列表请求参数")
public class ActivityListReq {
    /**
     * 活动类型 0.其他 1.游戏活动 2.直播活动 3.首页弹窗 4.直播间弹窗
     */
    @ApiModelProperty("活动类型 0.其他 1.游戏活动 2.直播活动 3.首页弹窗 4.直播间弹窗")
    private Integer activityType;

}
