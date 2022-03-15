package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员等级关联表
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemLevelLink implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 等级id
     */
    private Long levelId;

    /**
     * 会员id
     */
    private Long userId;

    /**
     * 商户code值，默认值为0
     */
    private String merchantCode;

    /**
     * 创建时间
     */
    private Date createTime;

}
