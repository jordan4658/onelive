package com.onelive.api.service.lottery.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.api.service.lottery.LotteryService;
import com.onelive.common.mybatis.entity.Lottery;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 彩种 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
@Service
public class LotteryServiceImpl extends ServiceImpl<LotteryMapper, Lottery> implements LotteryService {

    @Resource
    private SlaveLotteryMapper slaveLotteryMapper;

    @Override
    public Lottery getLotteryById(Integer id) {
        return slaveLotteryMapper.selectById(id);
    }

    @Override
    public Lottery getLotteryByLotteryId(Integer lotteryId) {
        QueryWrapper<Lottery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Lottery::getLotteryId,lotteryId);
        return slaveLotteryMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Lottery> listWithCurrentLang() {
        return slaveLotteryMapper.queryLotteryWithLang(LoginInfoUtil.getLang());
    }

	@Override
	public Lottery getByLotteryId(Long lotteryId, String lang) {
		return slaveLotteryMapper.getByLotteryId(lotteryId, lang);
	}
}
