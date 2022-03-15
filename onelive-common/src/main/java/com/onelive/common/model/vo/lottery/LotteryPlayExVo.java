package com.onelive.common.model.vo.lottery;

import com.onelive.common.mybatis.entity.LotteryPlay;
import lombok.Data;

/**
 * 玩法扩展类, 用于国际化
 */
@Data
public class LotteryPlayExVo extends LotteryPlay {
    /**
     * 用于展示的名称
     */
    private String showName;

}
