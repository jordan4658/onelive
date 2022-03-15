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
 * 第三方游戏平台
 * </p>
 *
 * @author ${author}
 * @since 2022-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameThirdPlatform implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 第三方平台名称
     */
    private String name;

    /**
     * 平台代码
     */
    private String platformCode;

    /**
     * 是否显示 0否1是
     */
    private Boolean isShow;

    /**
     * 是否删除 0否1是
     */
    private Boolean isDelete;

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
