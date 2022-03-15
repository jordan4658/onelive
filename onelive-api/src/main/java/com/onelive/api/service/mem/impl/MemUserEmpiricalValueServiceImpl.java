package com.onelive.api.service.mem.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.mem.MemUserEmpiricalValueService;
import com.onelive.common.mybatis.entity.MemUserEmpiricalValue;
import com.onelive.common.mybatis.mapper.master.mem.MemUserEmpiricalValueMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
@Service
public class MemUserEmpiricalValueServiceImpl extends ServiceImpl<MemUserEmpiricalValueMapper, MemUserEmpiricalValue> implements MemUserEmpiricalValueService {

    @Override
    public MemUserEmpiricalValue getByUserId(Long userId) {
        QueryWrapper<MemUserEmpiricalValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemUserEmpiricalValue::getUserId,userId).last("limit 1");
        return this.getBaseMapper().selectOne(queryWrapper);
    }
}
