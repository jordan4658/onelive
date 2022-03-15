package com.onelive.manage.service.lottery.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.LotteryCountry;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryCountryMapper;
import com.onelive.manage.service.lottery.LotteryCountryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 彩票-国家对应表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
@Service
public class LotteryCountryServiceImpl extends ServiceImpl<LotteryCountryMapper, LotteryCountry> implements LotteryCountryService {

}
