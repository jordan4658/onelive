package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 游戏分类标签, 用于前端显示
 * </p>
 *
 * @author ${author}
 * @since 2022-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameTag implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 国家code 关联sys_country.country_code 例如: zh_CN
     */
    private String countryCode;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 唯一标识
     */
    private String code;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图片地址
     */
    private String iconUrl;

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
