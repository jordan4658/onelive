package com.onelive.common.service.accountChange.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.service.accountChange.MemInfoService;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.mapper.master.mem.MemUserMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 基础用户表 服务实现类
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-01
 */
@Service
public class MemInfoServiceImpl extends ServiceImpl<MemUserMapper, MemUser> implements MemInfoService {

    @Resource
    private SlaveMemUserMapper slaveMemUserMapper;

    @Override
    public MemUser queryByAccount(String account) {
        QueryWrapper<MemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemUser::getUserAccount, account).last("limit 1 ");
        return slaveMemUserMapper.selectOne(queryWrapper);
    }


}
