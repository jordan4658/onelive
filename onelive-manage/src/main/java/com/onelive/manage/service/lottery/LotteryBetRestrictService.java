package com.onelive.manage.service.lottery;

import com.onelive.common.mybatis.entity.LotteryBetRestrict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 投注限制表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
public interface LotteryBetRestrictService extends IService<LotteryBetRestrict> {

    /**
     * 通过玩法id获取投注限制记录
     * @param playId
     * @return
     */
    LotteryBetRestrict getBetRestrictByPlayId(Integer playId);

}
