package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 第三方游戏分类保存请求参数
 */
@Data
@ApiModel("第三方游戏分类实体类")
public class GameCategoryVO {
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;
    @ApiModelProperty(name = "分类名称")
    private String name;
    /**
     * 分类ID
     */
    @ApiModelProperty("分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "第三方游戏平台代码")
    private String platformCode;
    /**
     * API基础接口
     */
    @ApiModelProperty("API基础接口")
    private String infoHost;

    /**
     * API数据接口
     */
    @ApiModelProperty("API数据接口")
    private String dataHost;

    /**
     * md5密钥
     */
    @ApiModelProperty("md5密钥")
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

    /**
     * 是否开售 false否 true是
     */
    @ApiModelProperty("是否开售 false否 true是")
    private Boolean isWork;

    @ApiModelProperty(name = "排序")
    private Integer sort;

    /**
     * 游戏类型
     */
    @ApiModelProperty("游戏类型")
    private String gameType;

    /**
     * 用于前端显示分类, 例如:视讯, 棋牌..
     */
//    @ApiModelProperty("用于前端显示分类")
//    private String type;

    /**
     * 商户地址, 登陆失败时，平台跳回商户的首页地址
     */
    @ApiModelProperty("商户地址")
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
    @ApiModelProperty("分类钱包")
    private Integer gameWallet;
}
