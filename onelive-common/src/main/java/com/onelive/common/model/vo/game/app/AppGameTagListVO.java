package com.onelive.common.model.vo.game.app;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 第三方游戏TAG分类标签列表查询实体类
 */
@Data
@ApiModel("第三方游戏TAG分类标签列表查询实体类")
public class AppGameTagListVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 类型名称
     */
    @ApiModelProperty("类型名称")
    private String name;

    /**
     * 唯一标识
     */
    @ApiModelProperty("唯一标识")
    private String code;
    /**
     * 排序
     */
//    @ApiModelProperty("排序")
//    private Integer sort;

    /**
     * icon图标
     */
    @ApiModelProperty("icon图标")
    private String iconUrl;

    public void setIconUrl(String iconUrl) {
        this.iconUrl = AWSS3Util.getAbsoluteUrl(iconUrl);
    }
}
