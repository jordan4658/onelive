package com.onelive.common.mybatis.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 第三方游戏汇率
 * </p>
 *
 * @author ${author}
 * @since 2021-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameOutsideGamesRate implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 游戏id
     */
    private Long gameId;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 转入汇率
     */
    private BigDecimal inRate;

    /**
     * 转出汇率
     */
    private BigDecimal outRate;

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
