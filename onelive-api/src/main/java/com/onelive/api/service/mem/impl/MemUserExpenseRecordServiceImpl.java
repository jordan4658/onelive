package com.onelive.api.service.mem.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.mem.MemUserExpenseRecordService;
import com.onelive.common.config.RabbitConfig;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.mybatis.entity.MemUserExpenseRecord;
import com.onelive.common.mybatis.mapper.master.mem.MemUserExpenseRecordMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserExpenseRecordMapper;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
@Service
public class MemUserExpenseRecordServiceImpl extends ServiceImpl<MemUserExpenseRecordMapper, MemUserExpenseRecord> implements MemUserExpenseRecordService {

    @Resource
    private SlaveMemUserExpenseRecordMapper slaveMemUserExpenseRecordMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void addExpenseRecord(Long userId, BigDecimal amount, Integer expenseType, String studioNum) {
        if (userId == null || expenseType == null || amount == null || amount.doubleValue() <= 0) {     //增加的经验值不可以小于0
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        //消费记录
        MemUserExpenseRecord record = new MemUserExpenseRecord();
        record.setUserId(userId);
        record.setAmount(amount);
        record.setType(expenseType);
        if(StrUtil.isNotBlank(studioNum)){
            record.setStudioNum(studioNum);
        }
        this.save(record);
        //mq异步统计消费总金额
        rabbitTemplate.convertAndSend(RabbitConfig.EXPENSE_AMOUNT_EXCHANGE_TOPIC, "add.expense", record);
    }

}
