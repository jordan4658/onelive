package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 第三方游戏多语言
 * </p>
 *
 * @author ${author}
 * @since 2021-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameCenterGameLang implements Serializable {

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
     * 游戏ID, 关联game_center_game.id
     */
    private Long gameId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 商户号
     */
    private String merchantCode;


}
