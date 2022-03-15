package com.onelive.manage.service.lottery;

import com.onelive.common.model.dto.lottery.LotteryInfoDTO;
import com.onelive.common.mybatis.entity.Lottery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 彩种 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
public interface LotteryService extends IService<Lottery> {

    SlaveLotteryMapper getSlave();

    /**
     * 移动端 根据lang查询所有彩种玩法赔率
     *
     * @return
     */
    List<LotteryInfoDTO> queryLotteryAllInfo(String lang);


    /**
     * 查询所有彩票，根据彩种分类
     *
     * @return
     */
    Map<Integer, List<Lottery>> queryLotteryGroupByCategoryIdWithLang(String lang);

    /**
     * 更新保存彩种赔率版本和ZIP下载地址
     */
    void updateLotteryVersionZIP(String zipDownURL, String lang);

    /**
     * 通过彩种Id，获取彩票信息
     * @param lotteryId
     * @return
     */
    Lottery getLotteryByLotteryId(Integer lotteryId);

    /**
     * 通过大类id，获取彩票信息列表
     * @param categoryId
     * @return
     */
    List<Lottery> getLotteryByCategoryId(Integer categoryId);

    /**
     * 查询指定语言的所有彩票
     * @param lang
     * @return
     */
    List<Lottery> queryLotteryWithLang(String lang);
}
