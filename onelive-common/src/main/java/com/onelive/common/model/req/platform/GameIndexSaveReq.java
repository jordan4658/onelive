package com.onelive.common.model.req.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("首页游戏配置保存请求参数")
public class GameIndexSaveReq {
    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;

    @ApiModelProperty("国家code 如zh_CN [必填]")
    private String countryCode;

    /**
     * 1.链接 2.游戏 3.APP内
     */
    @ApiModelProperty("跳转模式 1.链接 2.游戏 3.APP内 [必填]")
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
    @ApiModelProperty("游戏分类ID [必填]")
    private Integer categoryId;

    @ApiModelProperty("游戏唯一标识 [必填]")
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
    private String source = "recommend";

    @ApiModelProperty("关联活动ID")
    private Long actId;

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
    @ApiModelProperty("是否显示 [必填]")
    private Boolean isShow;

    @ApiModelProperty("多语言列表 [必填]")
    private List<GameIndexLangSaveReq> langList;

}
