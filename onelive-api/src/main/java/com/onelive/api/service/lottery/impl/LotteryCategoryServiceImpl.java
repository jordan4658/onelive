package com.onelive.api.service.lottery.impl;

import com.onelive.api.service.lottery.LotteryCategoryService;
import com.onelive.common.mybatis.entity.LotteryCategory;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryCategoryMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 彩种分类 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
@Service
public class LotteryCategoryServiceImpl extends ServiceImpl<LotteryCategoryMapper, LotteryCategory> implements LotteryCategoryService {

}
