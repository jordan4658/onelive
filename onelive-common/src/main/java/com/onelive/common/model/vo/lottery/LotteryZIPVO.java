package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种赔率玩法返回类")
public class LotteryZIPVO {

    @ApiModelProperty(value = "下载地址")
   private String downURL;

    @ApiModelProperty(value = "更新版本号")
    private String version;

    @ApiModelProperty(value = "语言")
    private String lang;
}
