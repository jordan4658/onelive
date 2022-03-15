package com.onelive.manage.service.live.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.LiveGameTag;
import com.onelive.common.mybatis.mapper.master.live.LiveGameTagMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveGameTagMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.live.LiveGameTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-06
 */
@Service
public class LiveGameTagServiceImpl extends ServiceImpl<LiveGameTagMapper, LiveGameTag> implements LiveGameTagService {
    @Resource
    private SlaveLiveGameTagMapper slaveLiveGameTagMapper;

    @Override
    public LiveGameTag getByCode(String code) {
        QueryWrapper<LiveGameTag> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveGameTag::getIsDelete,false).eq(LiveGameTag::getCode,code).eq(LiveGameTag::getMerchantCode, LoginInfoUtil.getMerchantCode()).last(" LIMIT 1");
        return slaveLiveGameTagMapper.selectOne(queryWrapper);
    }
}
