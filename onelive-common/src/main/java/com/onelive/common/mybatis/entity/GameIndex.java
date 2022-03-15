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
 * 首页游戏内容配置
 * </p>
 *
 * @author ${author}
 * @since 2022-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameIndex implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标url
     */
    private String iconUrl;

    /**
     * 国家地区code 如zh_CN
     */
    private String countryCode;

    /**
     * 1.链接 2.游戏 3.直播间 4.APP内
     */
    private Integer skipModel;

    /**
     * 跳转链接
     */
    private String skipUrl;

    /**
     * 1:原生页面 2:原生H5 3:浏览器
     */
    private Integer skipType;

    /**
     * 关联sys_adv_activity.id
     */
    private Long actId;

    /**
     * 关联game_category.category_id
     */
    private Integer categoryId;

    /**
     * 关联game_third.code
     */
    private String gameCode;

    /**
     * 关联live_studio_list.studio_num
     */
    private String studioNum;

    /**
     * 直播间跳转来源
     */
    private String source;

    /**
     * 页面路由
     */
    private String route;

    /**
     * 页面跳转参数
     */
    private String params;

    /**
     * 是否显示 0否1是
     */
    private Boolean isShow;

    /**
     * 是否删除 0否1是
     */
    private Boolean isDelete;

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

    /**
     * 商户号
     */
    private String merchantCode;


}
