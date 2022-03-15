package com.onelive.api.service.lottery.impl;

import com.onelive.api.service.lottery.LotteryDbGameService;
import com.onelive.common.mybatis.entity.LotteryDbGame;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryDbGameMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * JDB游戏字典表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Service
public class LotteryDbGameServiceImpl extends ServiceImpl<LotteryDbGameMapper, LotteryDbGame> implements LotteryDbGameService {

}
