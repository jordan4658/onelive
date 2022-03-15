package com.onelive.manage.service.lottery;

import com.onelive.common.model.vo.lottery.LotteryPlayOddsExVo;
import com.onelive.common.mybatis.entity.LotteryPlayOdds;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 赔率配置表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
public interface LotteryPlayOddsService extends IService<LotteryPlayOdds> {


    /**
     * 根据配置id查询相关赔率信息
     *
     * @param settingId 配置id
     * @return
     */
    List<LotteryPlayOdds> selectOddsListBySettingId(Integer settingId);

    /**
     * 查询所有赔率设置List集合
     *
     * @return
     * @param lang
     */
    List<LotteryPlayOddsExVo> selectPlayOddsListWithLang(String lang);


    /**
     * 查询玩法赔率
     *
     * @param playSettingList
     * @param lang
     * @return Map<#   settingId   ,   #   List   <   LotteryPlayOdds>>
     */
    Map<Integer, List<LotteryPlayOddsExVo>> selectOddsListBySettingIdListWithLang(List<Integer> playSettingList, String lang);

    /**
     * 获取指定规则下的赔率列表
     * @param settingId
     * @return
     */
    List<LotteryPlayOdds> queryPlayOddsBySettingId(Integer settingId);

}
