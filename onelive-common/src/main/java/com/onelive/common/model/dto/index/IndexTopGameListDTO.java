package com.onelive.common.model.dto.index;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("首页游戏配置列表实体类")
public class IndexTopGameListDTO {
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 图标url
     */
    @ApiModelProperty("图标url")
    private String iconUrl;

    /**
     * 1.链接 2.游戏 3.直播间 4.APP内
     */
    @ApiModelProperty("跳转类型 1.链接 2.游戏 3.直播间 4.APP内")
    private Integer skipModel;

    /**
     * 跳转链接
     */
    @ApiModelProperty("跳转链接")
    private String skipUrl;

    /**
     * 1:原生页面 2:原生H5 3:浏览器
     */
    @ApiModelProperty("链接跳转类型 1:原生页面 2:原生H5 3:浏览器")
    private Integer skipType;

    /**
     * 关联game_category.category_id
     */
    @ApiModelProperty("游戏分类ID")
    private Integer categoryId;

    /**
     * 关联game_third.game_id
     */
    @ApiModelProperty("游戏code")
    private String gameCode;

    /**
     * 关联live_studio_list.studio_num
     */
    @ApiModelProperty("直播间号")
    private String studioNum;

    /**
     * 直播间跳转来源
     */
    @ApiModelProperty("直播间跳转来源")
    private String source;

    /**
     * 页面路由
     */
    @ApiModelProperty("页面路由")
    private String route;

    /**
     * 页面跳转参数
     */
    @ApiModelProperty("页面跳转参数")
    private String params;
}
