package com.onelive.api.service.mem.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.mem.MemUserExpenseAmountService;
import com.onelive.common.mybatis.entity.MemUserExpenseAmount;
import com.onelive.common.mybatis.mapper.master.mem.MemUserExpenseAmountMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserExpenseAmountMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
@Service
public class MemUserExpenseAmountServiceImpl extends ServiceImpl<MemUserExpenseAmountMapper, MemUserExpenseAmount> implements MemUserExpenseAmountService {

    @Resource
    private SlaveMemUserExpenseAmountMapper slaveMemUserExpenseAmountMapper;

    @Override
    public MemUserExpenseAmount getByUserId(Long userId) {
        QueryWrapper<MemUserExpenseAmount> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemUserExpenseAmount::getUserId,userId).last("limit 1");
        return this.getBaseMapper().selectOne(queryWrapper);
    }

}
