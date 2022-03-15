package com.onelive.manage.service.lottery.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.LotteryPlaySetting;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryPlaySettingMapper;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryPlaySettingMapper;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.manage.service.lottery.LotteryPlaySettingService;
import com.onelive.manage.utils.redis.LotteryBusinessRedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Service
public class LotteryPlaySettingServiceImpl extends ServiceImpl<LotteryPlaySettingMapper, LotteryPlaySetting> implements LotteryPlaySettingService {

    @Resource
    private SlaveLotteryPlaySettingMapper slaveLotteryPlaySettingMapper;



    @Override
    public List<LotteryPlaySetting> queryLotteryPlaySettingWithLang(List<Integer> playIdList, String lang) {
        List<LotteryPlaySetting> lotteryPlaySettingList = getLotteryPlaySettingsFromCacheWithLang(lang);
        if (!CollectionUtils.isEmpty(playIdList)) {
            Map playIdMap = CollectionUtil.convertToMap(playIdList);
            lotteryPlaySettingList = lotteryPlaySettingList
                    .parallelStream()
                    .filter(item -> playIdMap.containsKey(item.getPlayId()))
                    .collect(Collectors.toList());
        }
        return lotteryPlaySettingList;
    }

    @Override
    public Map<Integer, LotteryPlaySetting> queryLotteryPlaySettingMapWithLang(List<Integer> playIdList, String lang) {
        Map<Integer, LotteryPlaySetting> map = new HashMap<>();
        List<LotteryPlaySetting> lotteryPlaySettings = this.queryLotteryPlaySettingWithLang(playIdList,lang);
        for (LotteryPlaySetting setting : lotteryPlaySettings) {
            map.put(setting.getPlayId(), setting);
        }
        return map;
    }

    @Override
    public LotteryPlaySetting querySettingByPlayId(Integer playId) {
        QueryWrapper<LotteryPlaySetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryPlaySetting::getPlayId,playId);
        queryWrapper.lambda().eq(LotteryPlaySetting::getIsDelete,false);
        return this.baseMapper.selectOne(queryWrapper);
    }


    private List<LotteryPlaySetting> getLotteryPlaySettingsFromCacheWithLang(String lang) {
        List<LotteryPlaySetting> lotteryPlaySettingList = LotteryBusinessRedisUtils.getLotteryPlaySettingsWithLang(lang);
        if (!CollectionUtils.isEmpty(lotteryPlaySettingList)) {
            return lotteryPlaySettingList;
        }
        lotteryPlaySettingList = slaveLotteryPlaySettingMapper.listAllPlaySettingsWithLang(lang);
        LotteryBusinessRedisUtils.setLotteryPlaySettingsWithLang(lotteryPlaySettingList,lang);
        return lotteryPlaySettingList;
    }

}
