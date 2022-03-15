package com.onelive.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 第三方游戏上下分转账记录表
 * </p>
 *
 * @author ${author}
 * @since 2021-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameTransferRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 转账流水号
     */
    private String transferNo;

    /**
     * 关联用户ID mem_user.id
     */
    private Long userId;

    /**
     * 上下分金额
     */
    private BigDecimal amount;

    /**
     * 关联game_category.category_id
     */
    private Integer categoryId;

    /**
     * 商户号
     */
    private String merchantCode;

    /**
     * 1:上分, 2:下分
     */
    private Integer type;

    /**
     * 0:成功 1:失败 2:转账中
     */
    private Integer transferStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
