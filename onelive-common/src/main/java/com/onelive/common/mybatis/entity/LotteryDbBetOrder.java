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
 * JDB注单表
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotteryDbBetOrder implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 游戏序号
     */
    private Long seqNo;

    /**
     * 玩家账号
     */
    private String playerId;

    /**
     * 游戏时间
     */
    private Date gameDate;

    /**
     * 游戏型态
     */
    private Integer gType;

    /**
     * 机台类型
     */
    private Integer mType;

    /**
     * 游戏区域
     */
    private Integer roomType;

    /**
     * 货币
     */
    private String currency;

    /**
     * 押注金额
     */
    private BigDecimal bet;

    /**
     * 游戏赢分
     */
    private BigDecimal win;

    /**
     * 总输赢
     */
    private BigDecimal total;

    /**
     * 投注面值
     */
    private BigDecimal denom;

    /**
     * 进场金额
     */
    private BigDecimal beforeBalance;

    /**
     * 离场金额
     */
    private BigDecimal afterBalance;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    /**
     * 玩家登入 IP
     */
    private String playerIp;

    /**
     * 玩家从网页或行动装置登入
     */
    private String clientType;

    private Integer isHandle;

    /**
     * 游戏名称
     */
    private String gameName;

    /**
     * 用户ID
     */
    private Long memberId;

    /**
     * 创建时间
     */
    private Date createTime;


}
