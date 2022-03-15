package com.onelive.manage.service.lottery.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.mybatis.entity.LotteryBetRestrict;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryBetRestrictMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.manage.service.lottery.LotteryBetRestrictService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 投注限制表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Service
public class LotteryBetRestrictServiceImpl extends ServiceImpl<LotteryBetRestrictMapper, LotteryBetRestrict> implements LotteryBetRestrictService {

    @Override
    public LotteryBetRestrict getBetRestrictByPlayId(Integer playId) {
        QueryWrapper<LotteryBetRestrict> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryBetRestrict::getPlayTagId,playId);
        return this.getOne(queryWrapper);
    }
}
