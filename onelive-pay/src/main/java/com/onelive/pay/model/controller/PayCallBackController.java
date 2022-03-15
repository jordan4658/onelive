package com.onelive.pay.model.controller;

import com.alibaba.fastjson.JSONObject;
import com.onelive.common.annotation.SupperAccess;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.pay.ConfirmWithdrawHandleReq;
import com.onelive.common.model.req.pay.OfflineRechargeHandleReq;
import com.onelive.common.model.req.pay.callback.FuYingCallBackReq;
import com.onelive.common.model.req.pay.callback.VgPayCallBackReq;
import com.onelive.common.model.vo.pay.callback.DileiCallbackVo;
import com.onelive.common.model.vo.pay.callback.KgDepositCallBackVO;
import com.onelive.common.utils.pay.Result;
import com.onelive.common.utils.pay.SignMd5Utils;
import com.onelive.pay.model.business.PayCallBackBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayCallBackController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/12 16:22
 */
@Slf4j
@RestController
@RequestMapping("/callback")
@Api(tags = "支付回调api")
public class PayCallBackController {

    @Resource
    private PayCallBackBusiness payCallBackBusiness;


    public static void main(String[] args) {
        String result="{\n" +
                "    \"code\":\"SUCCESS\",\n" +
                "    \"merchantCode\":\"61cbec1e1717202bfe09c875\",\n" +
                "    \"actualAmount\":2000000,\n" +
                "    \"sign\":\"a682bd955c3f726e3a6324d33ff46cfc\",\n" +
                "    \"orderStatus\":\"COMPLETED\",\n" +
                "    \"message\":\"订单完成\",\n" +
                "    \"merchantOrderNo\":\"CZ100012016\",\n" +
                "    \"orderAmount\":2000000,\n" +
                "    \"platformOrderNo\":\"61d2e3f789806339b482b333\",\n" +
                "    \"signType\":\"MD5\",\n" +
                "    \"actualFee\":24000\n" +
                "}";
        KgDepositCallBackVO backVO=JSONObject.parseObject(result,KgDepositCallBackVO.class);
        log.info("KG支付回调参数==============================kgDepositCallBackVO:"+ JSONObject.toJSONString(backVO));
        Map<String, Object> metaSignMap = new TreeMap<>();
        metaSignMap.put("merchantCode", backVO.getMerchantCode());
        metaSignMap.put("signType", backVO.getSignType());
//        metaSignMap.put("sign", backVO.getSign());
        metaSignMap.put("code", backVO.getCode());
        metaSignMap.put("message", backVO.getMessage());
        metaSignMap.put("merchantOrderNo", backVO.getMerchantOrderNo());
        metaSignMap.put("platformOrderNo", backVO.getPlatformOrderNo());
        metaSignMap.put("orderAmount", backVO.getOrderAmount());
        metaSignMap.put("actualAmount", backVO.getActualAmount());
        metaSignMap.put("actualFee", backVO.getActualFee());
        metaSignMap.put("orderStatus", backVO.getOrderStatus());
        String signString = SignMd5Utils.commonSignByObject(metaSignMap, "b55957665ab8be43dc3dcac68f9ad5a6b7cacc92f31e736469d638638a952971");
        log.info("本地签名结果："+signString+"  回调签名："+backVO.getSign()  +"是否相等："+backVO.getSign().equals(signString));
    }


    @ApiOperation("测试======KG支付回调")
    @PostMapping("/test/KgCallback.json")
    @SupperAccess
    public String testKgCallback() {
        String result="{\n" +
                "    \"code\":\"SUCCESS\",\n" +
                "    \"merchantCode\":\"61cbec1e1717202bfe09c875\",\n" +
                "    \"actualAmount\":2000000,\n" +
                "    \"sign\":\"a682bd955c3f726e3a6324d33ff46cfc\",\n" +
                "    \"orderStatus\":\"COMPLETED\",\n" +
                "    \"message\":\"订单完成\",\n" +
                "    \"merchantOrderNo\":\"CZ100012016\",\n" +
                "    \"orderAmount\":2000000,\n" +
                "    \"platformOrderNo\":\"61d2e3f789806339b482b333\",\n" +
                "    \"signType\":\"MD5\",\n" +
                "    \"actualFee\":24000\n" +
                "}";
        KgDepositCallBackVO backVO=JSONObject.parseObject(result,KgDepositCallBackVO.class);
        return payCallBackBusiness.kgCallback(backVO);
    }

    @ApiOperation("KG支付回调")
    @PostMapping("/KgCallback.json")
    @SupperAccess
    public String kgCallback(@RequestBody  KgDepositCallBackVO kgDepositCallBackVO) {
        log.info("进入KG支付回调接口==============================");
        log.info("KG支付回调参数==============================kgDepositCallBackVO:"+ JSONObject.toJSONString(kgDepositCallBackVO));
        return payCallBackBusiness.kgCallback(kgDepositCallBackVO);
    }

    @ApiOperation("开发测试客服确认用户提现")
    @PostMapping("/test_confirm_withdraw.json")
    @ResponseBody
    public ResultInfo test_confirm_withdraw(ConfirmWithdrawHandleReq req) {
        log.info("开发测试客服确认用户提现==============================");
        payCallBackBusiness.test_confirm_withdraw(req);
        return ResultInfo.ok();
    }

}
