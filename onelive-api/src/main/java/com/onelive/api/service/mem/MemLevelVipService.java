package com.onelive.api.service.mem;

import com.onelive.common.mybatis.entity.MemLevelVip;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * VIP等级 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface MemLevelVipService extends IService<MemLevelVip> {

    /**
     * 通过等级获取vip等级信息
     * @param level
     * @return
     */
    MemLevelVip getVipLevelInfo(Integer level);

    /**
     * 根据经验值查询当前可以升级的最大等级
     * @param empiricalValue
     * @return
     */
    MemLevelVip getMaxUpgradeableLevelByEmpirical(BigDecimal empiricalValue);
}
