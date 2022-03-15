package com.onelive.common.model.dto.ranking;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 贡献值排行榜查询数据
 */
@Data
public class ContributionRankingDto implements Serializable {

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 消费金额
     */
    private BigDecimal amount;
}
