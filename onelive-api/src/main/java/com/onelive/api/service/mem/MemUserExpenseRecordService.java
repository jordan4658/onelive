package com.onelive.api.service.mem;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemUserExpenseRecord;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
public interface MemUserExpenseRecordService extends IService<MemUserExpenseRecord> {

    /**
     * 	添加消费记录 (已实现关联添加经验值,调用此方法后不需要再单独调用添加经验值记录了)
     * @param userId        用户ID
     * @param amount        消费金额
     * @param expenseType   消费类型
     * @param studioNum      在哪个直播间消费的, 如果没有不用写
     */
    void addExpenseRecord(Long userId, BigDecimal amount, Integer expenseType, String studioNum);

}
