package com.onelive.common.mybatis.mapper.slave.mem;

import com.onelive.common.mybatis.entity.MemLevelVip;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;

/**
 * <p>
 * VIP等级 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface SlaveMemLevelVipMapper extends BaseMapper<MemLevelVip> {

    MemLevelVip getMaxUpgradeableLevelByEmpirical(BigDecimal empiricalValue);
}
