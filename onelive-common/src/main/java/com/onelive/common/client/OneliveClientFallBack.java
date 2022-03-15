package com.onelive.common.client;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.lottery.OrderBetDTO;
import com.onelive.common.model.req.lottery.LotteryDetailReq;
import com.onelive.common.model.req.lottery.LotteryIdsReq;
import com.onelive.common.model.req.lottery.OrderFollowReq;
import com.onelive.common.model.req.lottery.OrderReq;
import com.onelive.common.model.req.order.OrderBackReq;
import com.onelive.common.model.vo.lottery.OrderBetVO;
import com.onelive.common.model.vo.lottery.OrderTodayListVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ClassName OneliveClientFallBack
 * @Desc 订单服务回调
 * @Date 2021/3/15 14:41
 */
@Component
public class OneliveClientFallBack implements OneliveOrderClient {


    @Override
    public ResultInfo orderBet(OrderReq req) {
        return ResultInfo.error();
    }

    @Override
    public ResultInfo orderBetNew(OrderReq req) {
        return ResultInfo.error();
    }

    @Override
    public ResultInfo liveRoomCopy( OrderFollowReq req) {
        return ResultInfo.error();
    }

    @Override
    public ResultInfo getCaiDetail(LotteryDetailReq req) {
        return ResultInfo.error();
    }

    @Override
    public ResultInfo<PageInfo<OrderBetVO>> orderList(OrderBetDTO req) {
        return ResultInfo.error();
    }

    @Override
    public ResultInfo<OrderTodayListVO> orderTodayBetList(OrderBetDTO req) {
        return ResultInfo.error();
    }


    @Override
    public ResultInfo orderBack(OrderBackReq req) {
        return ResultInfo.error();
    }

    @Override
    public ResultInfo lishiSg(Integer pageNo, Integer pageSize, Integer id) {
        return ResultInfo.error();
    }

    @Override
    public ResultInfo jiesuanOrderBetById(Integer id, Double winAmount, String tbStatus, String openNumber) {
        return  ResultInfo.error();
    }

    @Override
    public ResultInfo jiesuanOrderBetByIssue(Integer lotteryId, String issue, String openNumber) {
        return  ResultInfo.error();
    }

    @Override
    public ResultInfo cancelOrderBetByIssue(Integer lotteryId, String issue) {
        return  ResultInfo.error();
    }

    @Override
    public ResultInfo queryLotteryList() {
        return  ResultInfo.error();
    }

    @Override
    public ResultInfo<List<Map<String, Object>>> bigOrSmall() {
        return  ResultInfo.error();
    }

    @Override
    public ResultInfo<List<Map<String, Object>>> singleAndDouble() {
        return  ResultInfo.error();
    }

    @Override
    public ResultInfo<List<Map<String, Object>>> fiveElements() {
        return  ResultInfo.error();
    }

    @Override
    public ResultInfo queryAllLotteryList() {
        return  ResultInfo.error();
    }

    @Override
    public ResultInfo<List<Map<String, Object>>> getSgLongDragons() {
        return  ResultInfo.error();
    }

    @Override
    public ResultInfo<Map<String, Object>> getNewestSgInfobyids(LotteryIdsReq req) {
        return  ResultInfo.error();
    }


}
    