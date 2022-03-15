package com.onelive.anchor.service;

import com.onelive.common.mybatis.entity.Lottery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 彩种 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
public interface LotteryService extends IService<Lottery> {

    /**
     * 通过id获取彩票，考虑从缓存读取或者mongodb读取
     * @param id
     * @return
     */
    Lottery getLotteryById(Integer id);

    /**
     * 通过彩种编号id获取彩票，考虑从缓存读取或者mongodb读取
     * @param lotteryId
     * @return
     */
    Lottery getLotteryByLotteryId(Integer lotteryId);

    /**
     * 根据当前语言查询彩票数据
     * @return
     */
    List<Lottery> listWithCurrentLang();

	/**
	 * 根据当前语言查询彩票数据
	 * @param gameId
	 * @param lang
	 * @return
	 */
	Lottery getByLotteryId(Long lotteryId, String lang);
}
