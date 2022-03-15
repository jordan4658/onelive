package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 提现快捷选项配置表
 * </p>
 *
 * @author ${author}
 * @since 2022-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaySilverBeanOptions implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 提现快捷选项主建ID
     */
      @TableId(value = "silver_bean_options_id", type = IdType.AUTO)
    private Long silverBeanOptionsId;

    /**
     * 多个值使用逗号分割开
     */
    private String optionsContent;


    /**
     * 是否删除：0-否，1是
     */
    private Boolean isDelete;

    /**
     * 是否启用：0-否，1是
     */
    private Boolean isEnable;

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
