package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 第三方游戏分类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameCategory implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 关联第三方游戏平台代码  game_third_platform.platform_code
     */
    private String platformCode;

    /**
     * API基础接口
     */
    private String infoHost;

    /**
     * API数据接口
     */
    private String dataHost;

    /**
     * md5密钥
     */
    private String md5Key;

    /**
     * AesKey加密参数
     */
    private String aesKey;

    /**
     * IV向量
     */
    private String iv;

    /**
     * 是否开售 0否1是
     */
    private Boolean isWork;

    /**
     * 状态 1停用 2启用
     */
    private Integer status;

    /**
     * 是否删除 0否1是
     */
    private Boolean isDelete;

    /**
     * 游戏类型
     */
    private String gameType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 商户地址, 登陆失败时，平台跳回商户的首页地址
     */
    private String backUrl;

    /**
     * 彩票电竞必须，动态游戏域名 通过游戏域名访问游戏
     */
    private String domain;

    /**
     * 商户号
     */
    private String merchantCode;

    /**
     * 游戏分类钱包字段, 区分不同分类不同的钱包,
OBG_ZR=1 真人游戏
OBG_TY=2 体育游戏
OBG_QP=3 棋牌游戏
OBG_BY=4 捕鱼游戏
OBG_LHJ=5 老虎机游戏
     */
    private Integer gameWallet;

    /**
     * 创建时间
     */
      @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;


}
