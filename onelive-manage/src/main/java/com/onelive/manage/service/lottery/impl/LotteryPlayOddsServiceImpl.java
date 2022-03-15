package com.onelive.manage.service.lottery.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.vo.lottery.LotteryPlayOddsExVo;
import com.onelive.common.mybatis.entity.LotteryPlayOdds;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryPlayOddsMapper;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryPlayOddsMapper;
import com.onelive.manage.service.lottery.LotteryPlayOddsService;
import com.onelive.manage.utils.redis.LotteryBusinessRedisUtils;
import com.onelive.manage.utils.redis.SysBusinessRedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 赔率配置表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Service
public class LotteryPlayOddsServiceImpl extends ServiceImpl<LotteryPlayOddsMapper, LotteryPlayOdds> implements LotteryPlayOddsService {

    @Resource
    private SlaveLotteryPlayOddsMapper slaveLotteryPlayOddsMapper;

    @Override
    public List<LotteryPlayOdds> selectOddsListBySettingId(Integer settingId) {
        List<LotteryPlayOdds> oddsList = SysBusinessRedisUtils.getOddsSettingList(settingId);
        if (CollectionUtils.isEmpty(oddsList)) {
            QueryWrapper<LotteryPlayOdds> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(LotteryPlayOdds::getSettingId,settingId);
            queryWrapper.lambda().eq(LotteryPlayOdds::getIsDelete,false);
            oddsList = slaveLotteryPlayOddsMapper.selectList(queryWrapper);
            SysBusinessRedisUtils.setOddsSettingList(oddsList,settingId);
        }
        return oddsList;
    }

    @Override
    public List<LotteryPlayOddsExVo> selectPlayOddsListWithLang(String lang) {
        // 获取所有配置信息
        List<LotteryPlayOddsExVo> oddsList = LotteryBusinessRedisUtils.getLotteryPlayOddsWithLang(lang); //SystemRedisUtils.getLotteryPlayOdds();
        if (CollectionUtils.isEmpty(oddsList)) {
            oddsList = slaveLotteryPlayOddsMapper.listAllPlayOddsWithLang(lang);
            LotteryBusinessRedisUtils.setLotteryPlayOddsWithLang(oddsList,lang);
        }
        return oddsList;

    }

    @Override
    public Map<Integer, List<LotteryPlayOddsExVo>> selectOddsListBySettingIdListWithLang(List<Integer> playSettingList, String lang) {
        Map<Integer, List<LotteryPlayOddsExVo>> map = new HashMap<>();
        // 查询所有赔率配置
        List<LotteryPlayOddsExVo> list = this.selectPlayOddsListWithLang(lang);
        if (!CollectionUtils.isEmpty(playSettingList)) {
            list = list.parallelStream().filter(odds -> playSettingList.contains(odds.getSettingId()))
                    .collect(Collectors.toList());
        }
        for (LotteryPlayOddsExVo odds : list) {
            List<LotteryPlayOddsExVo> lotteryPlayOddsList;
            if (map.containsKey(odds.getSettingId())) {
                lotteryPlayOddsList = map.get(odds.getSettingId());
            } else {
                lotteryPlayOddsList = new ArrayList<>();
            }
            lotteryPlayOddsList.add(odds);
            map.put(odds.getSettingId(), lotteryPlayOddsList);
        }
        return map;
    }

    @Override
    public List<LotteryPlayOdds> queryPlayOddsBySettingId(Integer settingId) {
        QueryWrapper<LotteryPlayOdds> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryPlayOdds::getSettingId,settingId);
        queryWrapper.lambda().eq(LotteryPlayOdds::getIsDelete,false);
        queryWrapper.lambda().orderByAsc(LotteryPlayOdds::getCreateTime);
        return this.baseMapper.selectList(queryWrapper);
    }
}
