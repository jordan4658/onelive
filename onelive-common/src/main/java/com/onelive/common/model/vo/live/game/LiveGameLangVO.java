package com.onelive.common.model.vo.live.game;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播分类游戏多语言实体类")
public class LiveGameLangVO {
    @ApiModelProperty("主键ID,更新时传入")
    private Long id;

    @ApiModelProperty("语言")
    private String lang;

    @ApiModelProperty("游戏名称")
    private String name;

    @ApiModelProperty("图片地址")
    private String iconUrl;

    public void setIconUrl(String iconUrl) {
        this.iconUrl = AWSS3Util.getAbsoluteUrl(iconUrl);
    }
}
