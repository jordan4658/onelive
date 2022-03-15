package com.onelive.api.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemUserEmpiricalRecord;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-15
 */
public interface MemUserEmpiricalRecordService extends IService<MemUserEmpiricalRecord> {

    /**
     * 增加用户经验值记录,升级用
     * @param userId            用户ID
     * @param empiricalValue    增加的经验值(只能是正数)
     * @param type              经验值类型, 关联枚举类{@link com.onelive.common.enums.UserEmpiricalTypeEnum}
     *   	GIFTS(1,"送礼物"),
     *		GAME(2,"玩游戏");
     */
    void addEmpiricalRecord(Long userId, BigDecimal empiricalValue, Integer type);

}
