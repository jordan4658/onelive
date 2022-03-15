package com.onelive.api.service.mem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.mem.MemUserEmpiricalRecordService;
import com.onelive.common.config.RabbitConfig;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.mybatis.entity.MemUserEmpiricalRecord;
import com.onelive.common.mybatis.mapper.master.mem.MemUserEmpiricalRecordMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-15
 */
@Service
public class MemUserEmpiricalRecordServiceImpl extends ServiceImpl<MemUserEmpiricalRecordMapper, MemUserEmpiricalRecord> implements MemUserEmpiricalRecordService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void addEmpiricalRecord(Long userId, BigDecimal empiricalValue, Integer empiricalType) {
        if (userId == null || empiricalType == null || empiricalValue == null || empiricalValue.doubleValue() <= 0) {     //增加的经验值不可以小于0
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        MemUserEmpiricalRecord record = new MemUserEmpiricalRecord();
        record.setUserId(userId);
        record.setEmpiricalValue(empiricalValue);
        record.setType(empiricalType);
        this.save(record);
        //mq异步计算经验值
        rabbitTemplate.convertAndSend(RabbitConfig.EMPIRICAL_VALUE_EXCHANGE_TOPIC, "add.empirical", record);
    }
}
