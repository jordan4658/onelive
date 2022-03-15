package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 第三方游戏分类保存请求参数
 */
@Data
@ApiModel("第三方游戏分类保存请求参数")
public class GameCategorySaveReq {
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;
    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称[必填]", required = true)
    private String name;
    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID[必填]",required = true)
    private Integer categoryId;
    /**
     * 关联第三方游戏平台ID  game_third_platform.id
     */
    @ApiModelProperty(value = "第三方游戏平台代码 [必填]",required = true)
    private String platformCode;
    /**
     * API基础接口
     */
    @ApiModelProperty(value = "API基础接口[必填]",required = true)
    private String infoHost;

    /**
     * API数据接口
     */
    @ApiModelProperty("API数据接口")
    private String dataHost;

    /**
     * md5密钥
     */
    @ApiModelProperty(value = "md5密钥[必填]",required = true)
    private String md5Key;

    /**
     * AesKey加密参数
     */
    @ApiModelProperty("AesKey加密参数")
    private String aesKey;

    /**
     * IV向量
     */
    @ApiModelProperty("IV向量")
    private String iv;

    @ApiModelProperty(value = "排序[必填]",required = true)
    private Integer sort;

    /**
     * 是否开售 false否 true是
     */
    @ApiModelProperty(value = "是否开售 false否 true是[必填]",required = true)
    private Boolean isWork;

    /**
     * 游戏类型
     */
    @ApiModelProperty(value = "游戏类型[必填]",required = true)
    private String gameType;

    /**
     * 商户地址, 登陆失败时，平台跳回商户的首页地址
     */
    @ApiModelProperty(value = "商户地址")
    private String backUrl;

    /**
     * 彩票电竞必须，动态游戏域名 通过游戏域名访问游戏
     */
    @ApiModelProperty("动态游戏域名")
    private String domain;

    /**
     * 游戏分类钱包字段, 区分不同分类不同的钱包,
     OBG_ZR=1 真人游戏
     OBG_TY=2 体育游戏
     OBG_QP=3 棋牌游戏
     OBG_BY=4 捕鱼游戏
     OBG_LHJ=5 老虎机游戏
     */
    @ApiModelProperty(value = "分类钱包[必填]",required = true)
    private Integer gameWallet;

}
