package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户中心游戏内容配置
 * </p>
 *
 * @author ${author}
 * @since 2022-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameUserCenter implements Serializable {

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
     * 关联game_category.category_id
     */
    private Integer categoryId;

    /**
     * 关联game_third.code
     */
    private String gameCode;

    /**
     * 是否删除 0否1是
     */
    private Boolean isDelete;

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

    /**
     * 商户号
     */
    private String merchantCode;


}
