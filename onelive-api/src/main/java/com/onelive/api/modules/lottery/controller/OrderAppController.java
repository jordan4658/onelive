package com.onelive.api.modules.lottery.controller;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.onelive.api.modules.lottery.business.OrderAppBusiness;
import com.onelive.common.base.BaseController;
import com.onelive.common.client.OneliveOrderClient;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.lottery.OrderBetDTO;
import com.onelive.common.model.dto.lottery.OrderLotteryDTO;
import com.onelive.common.model.req.lottery.*;
import com.onelive.common.model.req.order.BetInfoReq;
import com.onelive.common.model.req.order.OrderBackReq;
import com.onelive.common.model.vo.lottery.OrderBetVO;
import com.onelive.common.model.vo.lottery.OrderPushVo;
import com.onelive.common.model.vo.lottery.OrderTodayListVO;
import com.onelive.common.model.vo.order.BetInfoVO;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.redis.UserBusinessRedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/bet")
@Api(tags = "彩票投注")
//@ApiIgnore
@Slf4j
public class OrderAppController extends BaseController {

    @Resource
    private OneliveOrderClient oneliveOrderClient;
    @Resource
    private OrderAppBusiness orderAppBusiness;

    /**
     * 老彩种购彩投注
     *
     * @return
     */
    @ApiOperation("老彩种购彩投注")
    @RequestMapping(value = "/orderBet", method = RequestMethod.POST)
    public ResultInfo<Boolean> orderBet(@RequestBody(required = false) LotteryRequestInfo<OrderReq> requestInfo) {
        //检查参数
        if (requestInfo == null || requestInfo.getData() == null) {
            return ResultInfo.getInstance(StatusCode.PARAM_ERROR);
        }
        OrderReq req = requestInfo.getData();
        if (req == null
                || StringUtils.isBlank(req.getStudioNum())
                || StringUtils.isBlank(req.getIssue())
                || req.getLotteryId() == null
                || CollectionUtils.isEmpty(req.getOrderBetList())
        ) {
            return ResultInfo.getInstance(StatusCode.BUSINESS_ERROR);
        }
        //1、检测用户下单状态
        OrderLotteryDTO dto = new OrderLotteryDTO();
        BeanCopyUtil.copyProperties(req, dto);
        Long userId = LoginInfoUtil.getUserId();
        dto.setUserId(userId);
        orderAppBusiness.checkOrder(dto);
        //2、调用订单服务下注接口
        req.setUserId(userId.intValue());
        req.setSource(LoginInfoUtil.getSource());
        req.setCountryId(LoginInfoUtil.getCountryId());
        //把用户token存入Redis
        UserBusinessRedisUtils.setAppUserToken(userId,LoginInfoUtil.getToken());
        return orderAppBusiness.orderBet(req);
    }

    /**
     * 新彩种购彩投注
     *
     * @return
     */
    @ApiOperation("新彩种购彩投注")
    @RequestMapping(value = "/orderBetNew", method = RequestMethod.POST)
    public ResultInfo<Boolean> orderBetNew(@RequestBody(required = false) OrderReq req) {
        //检查参数
        if (req == null
                || StringUtils.isBlank(req.getStudioNum())
                || StringUtils.isBlank(req.getIssue())
                || req.getLotteryId() == null
                || CollectionUtils.isEmpty(req.getOrderBetList())) {
            return ResultInfo.getInstance(StatusCode.BUSINESS_ERROR);
        }
        //1、检测用户下单状态
        OrderLotteryDTO dto = new OrderLotteryDTO();
        BeanCopyUtil.copyProperties(req, dto);
        Long userId = LoginInfoUtil.getUserId();
        dto.setUserId(userId);
        orderAppBusiness.checkOrder(dto);
        //2、调用订单服务下注接口
        req.setUserId(userId.intValue());
        req.setSource(LoginInfoUtil.getSource());
        req.setCountryId(LoginInfoUtil.getCountryId());
        //把用户token存入Redis
        UserBusinessRedisUtils.setAppUserToken(userId,LoginInfoUtil.getToken());
        return oneliveOrderClient.orderBetNew(req);
    }

//    /**
//     * 彩票详情接口
//     * @param req
//     * @return
//     */
//    @ApiOperation("彩票详情接口")
//    @RequestMapping(value = "/getCaiDetail", method = RequestMethod.POST)
//    @AllowAccess
//    public ResultInfo<OrderBetRecordResultVO> getCaiDetail(@RequestBody(required = false) CaiDetailReq req) {
//        return oneliveOrderClient.getCaiDetail(req);
//    }

    /**
     * 直播间跟单
     *
     * @return
     */
    @ApiOperation("直播间跟单")
    @RequestMapping(value = "/liveRoomCopy", method = RequestMethod.POST)
    //@ApiIgnore
    public ResultInfo<OrderPushVo> liveRoomCopy(@RequestBody(required = false) OrderFollowReq req) {
        //检查参数
        if (req == null || StringUtils.isBlank(req.getStudioNum()) || CollectionUtils.isEmpty(req.getOrders())) {
            return ResultInfo.getInstance(StatusCode.PARAM_ERROR);
        }

        req.setUserId(LoginInfoUtil.getUserId().intValue());
        req.setSource(LoginInfoUtil.getSource());
        return oneliveOrderClient.liveRoomCopy(req);
    }


    /**
     * 投注列表
     *
     * @param
     * @param req
     * @return
     */
    @ApiOperation("用户投注列表记录")
    @RequestMapping(value = "/orderList", method = RequestMethod.POST)
    public ResultInfo<PageInfo<OrderBetVO>> orderList(@RequestBody(required = false) OrderBetReq req) {
        OrderBetDTO dto = new OrderBetDTO();
        BeanCopyUtil.copyProperties(req, dto);
        dto.setUserId(LoginInfoUtil.getUserId().intValue());
        //当地时间戳转日期
        if (req.getQueryDate() != null) {
            dto.setDate(DateUtil.date(req.getQueryDate()));
        }

        String lang = LoginInfoUtil.getLang();
        if(StringUtils.isBlank(lang)){
            lang = LangConstants.LANG_CN;
        }
        if(StringUtils.isBlank(req.getStatus())){
            dto.setStatus("Lottery");
        }
        dto.setLang(lang);
        dto.setPageNo(req.getPageNum());
        return oneliveOrderClient.orderList(dto);
    }

    /**
     * 今日投注金额 赢利 未结算 已结算
     */
//    @CheckLogin
    @ApiOperation("用户今日投注记录汇总")
    @PostMapping("/orderTodayBetList.json")
    public ResultInfo<OrderTodayListVO> orderTodayBetList(@RequestBody(required = false) TodayOrderBetReq req) {
        OrderBetDTO dto = new OrderBetDTO();
        BeanCopyUtil.copyProperties(req, dto);
        dto.setUserId(LoginInfoUtil.getUserId().intValue());
        return oneliveOrderClient.orderTodayBetList(dto);
    }


    /**
     * 获取历史开奖资讯
     *
     * @return
     */
    @PostMapping("/lishiSg.json")
    @ApiOperation("获取历史开奖资讯")
    public ResultInfo<Map<String, Object>> lishiSg(@RequestBody LotterySgReq req) {
        //检查参数
        if (req == null || null == req.getId()) {
            return ResultInfo.getInstance(StatusCode.PARAM_ERROR);
        }

        if (req.getPageNo() == null) {
            req.setPageNo(1);
        }
        if (req.getPageSize() == null) {
            req.setPageSize(10);
        }
        return oneliveOrderClient.lishiSg(req.getPageNo(), req.getPageSize(), req.getId());
    }


    @ApiOperation("跟投注单详情")
    @RequestMapping(value = "/getCaiDetail", method = RequestMethod.POST)
    //@ApiIgnore
    public ResultInfo getCaiDetail(@RequestBody(required = false) LotteryDetailReq req) {
        //检查参数
        if (req == null || req.getOrderId() == null) {
            return ResultInfo.getInstance(StatusCode.PARAM_ERROR);
        }
        return oneliveOrderClient.getCaiDetail(req);
    }


    /**
     * 撤单
     */
    @ApiOperation("撤单")
    @RequestMapping(value = "/orderBack.json", method = RequestMethod.POST)
    public ResultInfo orderBack(@RequestBody OrderBackReq req) {
        if (null == req.getOrderId()) {
            return ResultInfo.getInstance(StatusCode.PARAM_ERROR);
        }
        req.setUserId(LoginInfoUtil.getUserId().intValue());
        return oneliveOrderClient.orderBack(req);
    }


    @ApiOperation("根据订单ID查询下注信息,用于跟投")
    @RequestMapping(value = "/queryBetInfo", method = RequestMethod.POST)
    public ResultInfo<BetInfoVO> queryBetInfo(@RequestBody BetInfoReq req) {
        if (null == req.getOrderId()) {
            return ResultInfo.getInstance(StatusCode.PARAM_ERROR);
        }
        return ResultInfo.ok(orderAppBusiness.queryBetInfo(req));
    }


}
