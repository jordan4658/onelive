package com.onelive.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.onelive.common.constants.other.ServerConstants;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.pay.WithdrawReqFeign;
import com.onelive.common.model.vo.pay.PayWithdrawResultVO;

@FeignClient(value = ServerConstants.ONELIVE_PAY, fallback = PayClientFeignBack.class)
public interface PayClientFeignClient {

    String PAY_PREFIX = "/pay";

//    @PostMapping(value = PAY_PREFIX+"/app/v1/testFeign",headers = {"test_feign=this is feign header parameter"})  //不是动态传参
    @PostMapping(value = PAY_PREFIX+"/app/v1/testFeign")
    ResultInfo<?> testFeign(@RequestHeader("test_feign") String auth);  //动态传参

    @PostMapping(value = PAY_PREFIX+"/app/v1/feign/withdraw")
    ResultInfo<PayWithdrawResultVO> withdraw(@RequestBody WithdrawReqFeign withdrawReqFeign);

}
