package com.onelive.manage.service.mem.impl;

import com.onelive.common.mybatis.entity.MemLevelVip;
import com.onelive.common.mybatis.mapper.master.mem.MemLevelVipMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.manage.service.mem.MemLevelVipService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * VIP等级 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
@Service
public class MemLevelVipServiceImpl extends ServiceImpl<MemLevelVipMapper, MemLevelVip> implements MemLevelVipService {

    @Resource
    private MemLevelVipMapper memLevelVipMapper;

    @Override
    public Integer getMaxVipLevel() {

        return memLevelVipMapper.getMaxVipLevel();
    }
}
