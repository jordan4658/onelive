package com.onelive.api.service.mem;

import com.onelive.common.mybatis.entity.MemUserEmpiricalValue;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
public interface MemUserEmpiricalValueService extends IService<MemUserEmpiricalValue> {

    MemUserEmpiricalValue getByUserId(Long userId);
}
