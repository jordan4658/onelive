package com.onelive.common.model.vo.lottery;

import com.onelive.common.mybatis.entity.LotteryPlayOdds;
import lombok.Data;

/**
 * 赔率信息扩展类, 用于国际化
 */
@Data
public class LotteryPlayOddsExVo extends LotteryPlayOdds {
    /**
     * 用于展示的名称
     */
    private String showName;

}
