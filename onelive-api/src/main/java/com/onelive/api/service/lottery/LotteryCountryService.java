package com.onelive.api.service.lottery;

import com.onelive.common.model.vo.lottery.LotteryGameListVO;
import com.onelive.common.model.vo.lottery.LotteryRoomVO;
import com.onelive.common.mybatis.entity.LotteryCountry;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 彩票-国家对应表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
public interface LotteryCountryService extends IService<LotteryCountry> {

    List<LotteryRoomVO> getLotteryRoomList();

    /**
     * 获取当前地区彩种
     * @return
     */
    List<LotteryGameListVO> getLotteryGameList(String code);

    /**
     * 通过彩票id获取彩票信息
     * @param gameId
     * @return
     */
    LotteryCountry getLotteryByCode(Integer gameId);

}
