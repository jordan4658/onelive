package com.onelive.common.model.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 首页游戏配置信息
 */
@Data
@ApiModel("查询首页游戏配置信息实体类")
public class GameIndexVO {
    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("国家code 如zh_CN")
    private String countryCode;

    /**
     * 1.链接 2.游戏 3.直播间 4.APP内
     */
    @ApiModelProperty("跳转模式 1.链接 2.游戏 3.直播间 4.APP内")
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

    @ApiModelProperty("关联活动ID")
    private Long actId;

    /**
     * 关联game_category.category_id
     */
    @ApiModelProperty("游戏分类ID")
    private Integer categoryId;

    @ApiModelProperty("游戏唯一标识")
    private String gameCode;

    /**
     * 关联live_studio_list.studio_num
     */
    @ApiModelProperty("房间号")
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

    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("是否显示")
    private Boolean isShow;

    @ApiModelProperty("多语言列表")
    private List<GameIndexLangVO> langList;
}
