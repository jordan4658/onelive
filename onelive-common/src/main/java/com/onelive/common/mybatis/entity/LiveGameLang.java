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
 * 直播间游戏多语言
 * </p>
 *
 * @author ${author}
 * @since 2022-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveGameLang implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 语言
     */
    private String lang;

    /**
     * 游戏名称
     */
    private String name;

    /**
     * 游戏标签名称
     */
    private String iconUrl;

    /**
     * 游戏标签ID,关联live_game.id
     */
    private Long gameId;

    /**
     * 创建时间
     */
      @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 商户号
     */
    private String merchantCode;


}
