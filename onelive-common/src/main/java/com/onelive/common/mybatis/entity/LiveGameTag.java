package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2022-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LiveGameTag implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 地区列表
     */
    private String countryCodeList;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 标签code值
     */
    private String code;

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
