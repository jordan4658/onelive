package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 支付快捷选项表
 * </p>
 *
 * @author ${author}
 * @since 2022-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PayShortcutOptions implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 充值快捷选项ID
     */
      @TableId(value = "shortcut_options_id", type = IdType.AUTO)
    private Long shortcutOptionsId;


    /**
     * 支付方式ID
     */
    private Long payWayId;

    /**
     * 是否启用：0-否，1是
     */
    private Boolean isEnable;

    /**
     * 是否删除：0-否，1是
     */
    private Boolean isDelete;

    /**
     * 多个值使用逗号分割开
     */
    private String shortcutOptionsContent;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 最后更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;


}
