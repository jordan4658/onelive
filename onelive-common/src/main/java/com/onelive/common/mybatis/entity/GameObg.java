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
 * 第三方OBG游戏表, 用于同步第三方游戏列表
 * </p>
 *
 * @author ${author}
 * @since 2022-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameObg implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 游戏编号
     */
    private Long gameId;

    /**
     * 游戏名称
     */
    private String name;

    /**
     * 游戏图标
     */
    private String iconUrl;

    /**
     * 游戏分类id 关联game_category.category_id
     */
    private Integer categoryId;

    /**
     * 游戏平台代码, 关联game_third_platform.platform_code
     */
    private String platformCode;

    /**
     * 游戏运行状态：1：维护中，2：启用，3：停用
     */
    private Integer status;

    /**
     * 商户号
     */
    private String merchantCode;

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
