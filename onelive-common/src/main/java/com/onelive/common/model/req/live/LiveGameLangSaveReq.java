package com.onelive.common.model.req.live;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播分类游戏标签多语言保存请求参数")
public class LiveGameLangSaveReq {
    @ApiModelProperty(value = "主键ID, 更新时传入")
    private Long id;

    @ApiModelProperty(value = "语言[必填]",required = true)
    private String lang;

    @ApiModelProperty("游戏名称")
    private String name;

    @ApiModelProperty(value ="图片地址[必填]",required = true)
    private String iconUrl;

    public void setIconUrl(String iconUrl) {
        this.iconUrl = AWSS3Util.getRelativeUrl(iconUrl);
    }
}
