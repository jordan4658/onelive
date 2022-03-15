package com.onelive.manage.modules.operate.business;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.operate.GameRiskReq;
import com.onelive.common.model.vo.operate.GameRiskListVO;
import com.onelive.manage.service.lottery.LotteryOrderBetRecordService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 游戏风控业务类
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameRiskBusiness {

    @Resource
    private LotteryOrderBetRecordService lotteryOrderBetRecordService;

    /**
     * 查询游戏数据
     * @param req
     * @return
     */
    public PageInfo<GameRiskListVO> getGameRiskList(GameRiskReq req) {

        return lotteryOrderBetRecordService.countGameRiskListData(req);
    }
}
