package com.onelive.common.mybatis.mapper.master.mem;

import com.onelive.common.mybatis.entity.MemLevelVip;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * VIP等级 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface MemLevelVipMapper extends BaseMapper<MemLevelVip> {

    Integer getMaxVipLevel();
}
