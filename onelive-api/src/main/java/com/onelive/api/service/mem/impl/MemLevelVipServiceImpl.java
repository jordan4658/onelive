package com.onelive.api.service.mem.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.api.service.mem.MemLevelVipService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.mybatis.entity.MemLevelVip;
import com.onelive.common.mybatis.mapper.master.mem.MemLevelVipMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemLevelVipMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

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
    private SlaveMemLevelVipMapper slaveMemLevelVipMapper;

    @Override
    public MemLevelVip getVipLevelInfo(Integer level) {
        MemLevelVip vip =  ApiBusinessRedisUtils.getVipLevelInfo(level);
        if(vip == null){
            QueryWrapper<MemLevelVip> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(MemLevelVip::getLevelWeight,level);
            queryWrapper.lambda().eq(MemLevelVip::getMerchantCode, LoginInfoUtil.getMerchantCode()).last(" limit 1 ");
            vip =  slaveMemLevelVipMapper.selectOne(queryWrapper);
            if(vip != null){
                ApiBusinessRedisUtils.setVipLevelInfo(vip.getLevelWeight(), JSON.toJSONString(vip));
            }
        }
        return vip;
    }

    @Override
    public MemLevelVip getMaxUpgradeableLevelByEmpirical(BigDecimal empiricalValue) {
        return slaveMemLevelVipMapper.getMaxUpgradeableLevelByEmpirical(empiricalValue);
    }
}
