package com.onelive.api.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemUserExpenseAmount;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
public interface MemUserExpenseAmountService extends IService<MemUserExpenseAmount> {

    MemUserExpenseAmount getByUserId(Long userId);

}
