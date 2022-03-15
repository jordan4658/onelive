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
 * 彩票-国家对应表
 * </p>
 *
 * @author ${author}
 * @since 2022-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameCenterGame implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 游戏名称
     */
    private String name;

    /**
     * 游戏平台代码, 关联game_third_platform.platform_code
     */
    private String platformCode;

    /**
     * 标签唯一标识
     */
    private String code;

    /**
     * 游戏唯一标识
     */
    private String gameCode;

    /**
     * 游戏分类id,关联game_category.category_id
     */
    private Integer categoryId;

    /**
     * 图片地址
     */
    private String iconUrl;

    /**
     * 是否显示 0否1是
     */
    private Boolean isShow;

    /**
     * 游戏运行状态：1：维护中，2：启用，3：停用
     */
    private Integer status;

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
     * 后台创建人账号
     */
    private String createUser;

    /**
     * 更新时间
     */
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 后台更新人账号
     */
    private String updateUser;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
