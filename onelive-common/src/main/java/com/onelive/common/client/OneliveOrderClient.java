package com.onelive.common.client;


import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.other.ServerConstants;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.lottery.LotteryInfoDTO;
import com.onelive.common.model.dto.lottery.OrderBetDTO;
import com.onelive.common.model.req.lottery.LotteryDetailReq;
import com.onelive.common.model.req.lottery.LotteryIdsReq;
import com.onelive.common.model.req.lottery.OrderFollowReq;
import com.onelive.common.model.req.lottery.OrderReq;
import com.onelive.common.model.req.order.OrderBackReq;
import com.onelive.common.model.vo.lottery.OrderBetVO;
import com.onelive.common.model.vo.lottery.OrderTodayListVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = ServerConstants.LOTTERY_ORDER, fallback = OneliveClientFallBack.class)
public interface OneliveOrderClient {

    @RequestMapping(name = "老彩种购彩投注",value = "/bet/orderBet", method = RequestMethod.POST)
    ResultInfo orderBet(@RequestBody(required = false) OrderReq req);

    @RequestMapping(name = "新彩种购彩投注",value = "/bet/orderBetNew", method = RequestMethod.POST)
    ResultInfo orderBetNew(@RequestBody(required = false) OrderReq req);

    @RequestMapping(name = "直播间跟单",value = "/bet/liveRoomCopy", method = RequestMethod.POST)
    ResultInfo liveRoomCopy(@RequestBody(required = false) OrderFollowReq req);

    @RequestMapping(name = "彩票详情接口",value = "/bet/getCaiDetail", method = RequestMethod.POST)
    ResultInfo getCaiDetail(@RequestBody(required = false) LotteryDetailReq req);

    @RequestMapping(name = "投注列表",value = "/bet/orderList", method = RequestMethod.POST)
    ResultInfo<PageInfo<OrderBetVO>> orderList(@RequestBody(required = false) OrderBetDTO req);

    @RequestMapping(name = "今日投注金额 赢利 未结算 已结算",value = "/bet/orderTodayBetList.json", method = RequestMethod.POST)
    ResultInfo<OrderTodayListVO>  orderTodayBetList(@RequestBody(required = false) OrderBetDTO req);

    @RequestMapping(name = "撤单",value = "/bet/orderBack.json", method = RequestMethod.POST)
    ResultInfo orderBack(@RequestBody(required = false) OrderBackReq req);

    @RequestMapping(name = "查询app获取多个彩种的开奖历史信息",value = "/sg/lishiSg.json", method = RequestMethod.POST)
    ResultInfo lishiSg(@RequestParam("pageNo") Integer pageNo,
                       @RequestParam("pageSize") Integer pageSize, @RequestParam("id") Integer id);


    @RequestMapping(name = "根据用户id结算注单", value = "/manage/jiesuanOrderBetById", method = RequestMethod.POST)
    ResultInfo jiesuanOrderBetById(@RequestParam("id") Integer id, @RequestParam("winAmount") Double winAmount,
                                          @RequestParam("tbStatus") String tbStatus, @RequestParam("openNumber") String openNumber);

    @RequestMapping(name = "根据issue结算注单, 10分钟后才能 进行第二次期号结算", value = "/manage/jiesuanOrderBetByIssue", method = RequestMethod.POST)
    ResultInfo jiesuanOrderBetByIssue(@RequestParam("lotteryId") Integer lotteryId, @RequestParam("issue") String issue,
                                             @RequestParam("openNumber") String openNumber);

    @RequestMapping(name = "根据issue撤销注单", value = "/manage/cancelOrderBetByIssue", method = RequestMethod.POST)
    ResultInfo cancelOrderBetByIssue(@RequestParam(value = "lotteryId", required = false) Integer lotteryId, @RequestParam("issue") String issue);

    @RequestMapping(name = "获取彩种列表-非第三方", value = "/lotteryApp/queryLotteryList", method = RequestMethod.POST)
    ResultInfo queryLotteryList();

    @RequestMapping(name = "澳洲ACT大小", value = "/ausactSg/bigOrSmall.json", method = RequestMethod.POST)
    ResultInfo<List<Map<String, Object>>> bigOrSmall();

    @RequestMapping(name = "澳洲ACT单双", value = "/ausactSg/singleAndDouble.json", method = RequestMethod.POST)
    ResultInfo<List<Map<String, Object>>> singleAndDouble();

    @RequestMapping(name = "澳洲ACT五行", value = "/ausactSg/fiveElements.json", method = RequestMethod.POST)
    ResultInfo<List<Map<String, Object>>> fiveElements();

    @RequestMapping(name = "获取所有彩种配置，非第三方", value = "/lotteryApp/queryAllLotteryList", method = RequestMethod.POST)
    ResultInfo<List<LotteryInfoDTO>> queryAllLotteryList();

    @RequestMapping(name = "APP开奖模块长龙", value = "/sg/getSgLongDragons.json", method = RequestMethod.POST)
    ResultInfo<List<Map<String, Object>>> getSgLongDragons();

    @RequestMapping(name = "APP开奖模块首页彩种ID获取信息（官彩）首页", value = "/sg/getNewestSgInfobyids.json", method = RequestMethod.POST)
    ResultInfo<Map<String, Object>> getNewestSgInfobyids(LotteryIdsReq req);
}

