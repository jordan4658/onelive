package com.onelive.common.model.vo.game.app;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("第三方游戏TAG分类标签列表和标签中的游戏列表查询实体类")
public class AppGameTagListDataVO {
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
     * icon图标
     */
    @ApiModelProperty("icon图标")
    private String iconUrl;

    public void setIconUrl(String iconUrl) {
        this.iconUrl = AWSS3Util.getAbsoluteUrl(iconUrl);
    }


    @ApiModelProperty("游戏列表")
    List<AppGameCenterGameListVO> gameList;
}
