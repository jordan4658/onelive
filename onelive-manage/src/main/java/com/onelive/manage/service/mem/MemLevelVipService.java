package com.onelive.manage.service.mem;

import com.onelive.common.mybatis.entity.MemLevelVip;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * VIP等级 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface MemLevelVipService extends IService<MemLevelVip> {

    Integer getMaxVipLevel();
}
