package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 直播间游戏
 * </p>
 *
 * @author ${author}
 * @since 2022-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveGame implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 游戏名称,用于后台显示
     */
    private String name;

    /**
     * 关联live_game_tag.code值
     */
    private String code;

    /**
     * 默认图片地址
     */
    private String iconUrl;

    /**
     * 游戏类型8000=彩票, 其他关联game_category.category_id
     */
    private Integer categoryId;

    /**
     * 游戏ID, 关联lottery.lotteryId 或 game_third.code
     */
    private String gameCode;

    /**
     * 是否第三方游戏 0否1是
     */
    private Boolean isThird;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否删除 0否1是
     */
    private Boolean isDelete;

    /**
     * 是否显示 0否1是
     */
    private Boolean isShow;

    /**
     * 商户号
     */
    private String merchantCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;


}
