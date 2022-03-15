package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 图片管理表
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysImageInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 说明
     */
    private String note;

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
     * 是否删除
     */
    private Boolean isDelete;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;


}
