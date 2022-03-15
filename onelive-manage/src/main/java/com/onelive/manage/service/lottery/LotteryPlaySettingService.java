package com.onelive.manage.service.lottery;

import com.onelive.common.mybatis.entity.LotteryPlaySetting;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
public interface LotteryPlaySettingService extends IService<LotteryPlaySetting> {



    /**
     * 查询指定玩法的设置信息(走缓存，为了快速生成压缩包)
     *
     * @param playIdList
     * @param lang
     * @return
     */
    List<LotteryPlaySetting> queryLotteryPlaySettingWithLang(List<Integer> playIdList, String lang);

    /**
     * 查询指定玩法的设置信息
     *
     * @param playIdList
     * @param lang
     * @return Map<playId, # LotteryPlaySetting>
     */
    Map<Integer, LotteryPlaySetting> queryLotteryPlaySettingMapWithLang(List<Integer> playIdList, String lang);

    /**
     * 通过玩法id获取玩法设置
     * @param playId
     * @return
     */
    LotteryPlaySetting querySettingByPlayId(Integer playId);

}
