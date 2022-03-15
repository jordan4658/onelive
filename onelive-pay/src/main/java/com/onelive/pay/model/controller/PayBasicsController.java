package com.onelive.pay.model.controller;

import com.alibaba.fastjson.JSONObject;

import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.annotation.SupperAccess;
import com.onelive.common.base.BaseController;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.req.pay.*;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.model.vo.pay.*;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.http.HttpClient;
import com.onelive.common.utils.others.DateInnerUtil;
import com.onelive.common.utils.pay.PayUtils;
import com.onelive.common.utils.pay.SignMd5Utils;
import com.onelive.pay.model.business.OrderPayMentInfoBusiness;
import com.onelive.pay.model.business.PayBasicsBusiness;
import com.onelive.pay.model.business.PayWithdrawBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayInsertController
 * @Description: TODO(支付)
 * @date 创建时间：2021/4/7 12:10
 */
@Slf4j
@RestController
@RequestMapping("/pay")
@Api(tags = "支付相关api")
public class PayBasicsController extends BaseController {

    @Resource
    private PayBasicsBusiness payBasicsBusiness;

    @Resource
    private PayWithdrawBusiness payWithdrawBusiness;

    @Resource
    private OrderPayMentInfoBusiness orderPayMentInfoBusiness;

    @Resource
    private AccountBalanceChangeService accountBalanceChangeService;


    @ApiOperation("收到feign请求的头部参数")
    @PostMapping("/app/v1/testFeign")
    @SupperAccess
    public ResultInfo testFeign(HttpServletRequest request) {
        String test_feign = request.getHeader("test_feign");
        log.info("==================收到feign请求的头部参数test_feign：" + test_feign);
        return ResultInfo.ok();
    }

    @ApiOperation("查询-充值支付方式列表-未分页")
    @PostMapping("/app/v1/getPayWayList")
    public ResultInfo<List<PayWayVO>> getPayWayList() {
        List<PayWayVO> list = payBasicsBusiness.getPayWayList();
        return ResultInfo.ok(list);
    }

    @ApiOperation("根据支付方式ID-快捷充值选项列表")
    @PostMapping("/app/v1/getRechargeOptions")
    @SupperAccess
    public ResultInfo<List<RechargeShortcutOptionsVO>> getRechargeOptions(@RequestBody RechargeOptionsQueryReq req) {
        List<RechargeShortcutOptionsVO> list = payBasicsBusiness.getRechargeOptions(req);
        return ResultInfo.ok(list);
    }

    @ApiOperation("金币兑换银豆快捷选项列表")
    @PostMapping("/app/v1/getSilverBeanOptions")
    @SupperAccess
    public ResultInfo<List<SilverBeanOptionsVO>> getSilverBeanOptions() {
        List<SilverBeanOptionsVO> list = payBasicsBusiness.getSilverBeanOptions();
        return ResultInfo.ok(list);
    }


    @ApiOperation("充值下单接口")
    @PostMapping("/app/v1/recharge")
    public ResultInfo<JSONObject> rechargeOrder(@RequestBody RechargeAddReq rechargeAddReq) {
        AppLoginUser user = getLoginUserAPP();
        String requestIp = LoginInfoUtil.getIp();
        String source = LoginInfoUtil.getSource();
        JSONObject jsonObject = payBasicsBusiness.rechargeOrder(rechargeAddReq, user, source, requestIp);
        return ResultInfo.ok(jsonObject);
    }

    @ApiOperation("申请-提现取款接口")
    @PostMapping("/app/v1/withdraw")
    public ResultInfo<PayWithdrawResultVO> withdraw(@RequestBody WithdrawReq withdrawReq) {
        AppLoginUser user = getLoginUserAPP();
        String requestIp = LoginInfoUtil.getIp();
        String source = LoginInfoUtil.getSource();
        return ResultInfo.ok(payWithdrawBusiness.withdraw(withdrawReq, user, source, requestIp));
    }

    @ApiOperation("申请-提现取款接口(feign调用-专用)")
    @PostMapping("/app/v1/feign/withdraw")
    public ResultInfo<PayWithdrawResultVO> feignWithdraw(@RequestBody WithdrawReqFeign req) {
        AppLoginUser user = getLoginUserAPP();
        user.setId(req.getUserId());
        WithdrawReq withdrawReq=new WithdrawReq();
        withdrawReq.setPrice(req.getPrice());
        withdrawReq.setBankAccid(req.getBankAccid());
        return ResultInfo.ok(payWithdrawBusiness.withdraw(withdrawReq, user, req.getSource(), req.getRequestIp()));
    }

    @ApiOperation("取消-提现取款接口")
    @PostMapping("/app/v1/cancelWithdraw")
    public ResultInfo<JSONObject> cancelWithdraw(@RequestBody CancelWithdrawReq cancelWithdrawReq) {
        AppLoginUser user = getLoginUserAPP();
        payWithdrawBusiness.cancelWithdraw(cancelWithdrawReq, user);
        return ResultInfo.ok();
    }
    @ApiOperation("取消-提现取款接口(feign调用-专用)")
    @PostMapping("/app/v1/feign/cancelWithdraw")
    public ResultInfo<JSONObject> cancelWithdrawFeign(@RequestBody CancelWithdrawReqFeign req) {
        AppLoginUser user = getLoginUserAPP();
        user.setId(req.getUserId());
        CancelWithdrawReq cancelWithdrawReq=new CancelWithdrawReq();
        cancelWithdrawReq.setWithdrawOrderNo(req.getWithdrawOrderNo());
        cancelWithdrawReq.setCancelExplain(req.getCancelExplain());
        payWithdrawBusiness.cancelWithdraw(cancelWithdrawReq, user);
        return ResultInfo.ok();
    }


    @ApiOperation("根据充值订单号查询充值订单信息")
    @PostMapping("/app/v1/getOrderDetailsByOrderNo")
    public ResultInfo<MemGoldchangeVO> getOrderDetailsByOrderNo(@RequestBody OrderDetailsByOrderNoReq req) {
        AppLoginUser user = getLoginUserAPP();
        MemGoldchangeVO rechargeVO = orderPayMentInfoBusiness.getOrderDetailsByOrderNo(user, req);
        return ResultInfo.ok(rechargeVO);
    }

    @ApiOperation("根据提现订单号查询提现订单信息")
    @PostMapping("/app/v1/getWithdrawDetailsByOrderNo")
    public ResultInfo<PayWithdrawResultsVO> getWithdrawDetailsByOrderNo(@RequestBody WithdrawDetailsByOrderNoReq req) {
        AppLoginUser user = getLoginUserAPP();
        PayWithdrawResultsVO payWithdrawResultsVO = payWithdrawBusiness.getWithdrawDetailsByOrderNo(user, req);
        return ResultInfo.ok(payWithdrawResultsVO);
    }


    @ApiOperation("线下充值-取消接口")
    @PostMapping("/app/v1/cancelRecharge")
    public ResultInfo cancelRecharge(@RequestBody CancelRechargeReq req) {
        AppLoginUser user = getLoginUserAPP();
        payBasicsBusiness.cancelRecharge(req, user);
        return ResultInfo.ok();
    }


    @ApiOperation("公共账变金币、银豆-接口")
    @PostMapping("/app/v1/AccountBalanceChange")
    public ResultInfo<BalanceChangeDTO> AccountBalanceChange(@RequestBody MemAccountChangeVO changeVO) {
        return ResultInfo.ok(accountBalanceChangeService.publicAccountBalanceChange(changeVO));
    }


    public static void main(String[] args) {

        //预支付id（微信、支付宝  支付下单后返回的预支付id）
        log.info("KG支付业务处理开始================================================");
        JSONObject json = new JSONObject();
        try {

            //{"accountBank":"ACB","merchantCode":"61cbec1e1717202bfe09c875","pay_md5sign":"21e0ea188ea87f03aac05da3d3faae12",
            // "orderAmount":4638960,"method":"C2C_DIRECT","accountName":"还HBV","signType":"MD5","notifyUrl":
            // "http://54.180.119.120:4040/pay/callback/KgCallback.json",
            // "merchantOrderNo":"CZBKCZ0311873011254336","timestamp":"20220105223554"}
            //商户自定义订单号25位
            String merchantOrderNo = PayUtils.getOrderNo();
//            String merchantOrderNo = "CZ100012016";
            //支付方式
            String method = "C2C_DIRECT";
            //商户id
            String merchantCode = "61cbec1e1717202bfe09c875";
            //商户key
            String apikey = "b55957665ab8be43dc3dcac68f9ad5a6b7cacc92f31e736469d638638a952971";
            //第三方支付 下单地址
            String orderUrl = "https://api.kgpay.xyz/gateway/deposit";
            //获取平台服务的网关地址信息
            //回调地址 = 平台服务的网关地址+业务参数里面配置的
            String notifyUrl = "http://54.180.119.120:4040/pay/callback/KgCallback.json";
            Map<String, Object> map = new HashMap<>();
            map.put("merchantCode", merchantCode);
            map.put("method", method);
            map.put("signType", "MD5");
            map.put("timestamp", DateInnerUtil.getTime_yyyMMddHHmmss());
            map.put("merchantOrderNo", merchantOrderNo);
            map.put("orderAmount", 2000);
            map.put("accountName", "ACB");
            map.put("accountBank", "ACB");
            map.put("notifyUrl", notifyUrl);
            String signString = SignMd5Utils.commonSignByObject(map, apikey);
            log.info("========signString===========:" + signString);
            map.put("sign", signString);
            log.info("发起地KG支付参数-----:{}", JSONObject.toJSONString(map));
            String result = HttpClient.formPostByObject(orderUrl, map);
            log.info("KG支付--充值下单返回结果：" + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            log.info("KG支付 发起支付接口返回结果：" + JSONObject.toJSONString(jsonObject));
            if (jsonObject.containsKey("status")) {
                throw new RuntimeException("KG支付，报错：" + jsonObject.get("msg"));
            }

        } catch (Exception e) {
            log.error("KG支付出错", e);
            throw new BusinessException(e.getMessage());
        }
        log.info("KG支付业务处理结束================================================");
    }


}
