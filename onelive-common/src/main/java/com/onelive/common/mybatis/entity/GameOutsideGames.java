package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 第三方游戏
 * </p>
 *
 * @author ${author}
 * @since 2021-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameOutsideGames implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 游戏显示名称
     */
    private String showName;

    /**
     * 游戏分类
     */
    private String gameCategory;

    /**
     * logo图片地址
     */
    private String imgUrl;

    /**
     * 使用地区编码
     */
    private String countryCode;

    /**
     * 使用地区
     */
    private String countryName;

    /**
     * 状态 0启用1禁用
     */
    private Boolean isFrozen;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 后台创建人账号
     */
    private String createUser;

    /**
     * 更新时间
     */
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
