package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author harden
 * @Description: 直播间活动列表
 * @date 2021/4/5
 */
@Data
@ApiModel
public class LiveActListVO {

    @ApiModelProperty("活动主键")
    private Long id;

    @ApiModelProperty("活动图片地址")
    private String imgUrl;

    @ApiModelProperty("活动跳转地址")
    private String link;


}
