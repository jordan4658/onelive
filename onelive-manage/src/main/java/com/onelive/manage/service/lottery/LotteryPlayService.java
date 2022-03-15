package com.onelive.manage.service.lottery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.lottery.LotteryPlayAllDTO;
import com.onelive.common.model.vo.lottery.LotteryPlayExVo;
import com.onelive.common.mybatis.entity.LotteryPlay;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryPlayMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 彩种玩法 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
public interface LotteryPlayService extends IService<LotteryPlay> {

    /**
     * 获取从mapper
     * @return
     */
    SlaveLotteryPlayMapper getSlave();

    /**
     * 查询彩种下的所有玩法，根据彩票ID分组
     *
     * @param categoryIds 彩种 ID列表
     * @param lang
     * @return Map [ lotteryId, Map [ parentPlayId, Map [ childPlayId, lotteryPlay ]
     *         ] ]
     */
    Map<Integer, Map<Integer, Map<Integer, LotteryPlayExVo>>> selectAllLotteryPlayByCategoryIdsWithLang(List<Integer> categoryIds, String lang);


    /**
     * 查询指定彩种下的彩票的所有玩法
     *
     * @param categoryIds
     * @param lang
     * @return Map[LotteryId, List[LotteryPlayAllDTO]]
     */
    Map<Integer, List<LotteryPlayAllDTO>> selectAllLotteryPlayDTOByCategoryIdsWithLang(List<Integer> categoryIds, String lang);

    /**
     * 通过玩法id获取玩法
     * @param playId
     * @return
     */
    LotteryPlay queryLotteryPlayByPlayId(Integer playId);


    List<LotteryPlayExVo> listLotteryPlayWithLang(Integer lotteryId, String lang);
}
