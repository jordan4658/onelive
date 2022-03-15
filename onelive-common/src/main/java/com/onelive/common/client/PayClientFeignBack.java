package com.onelive.common.client;

import org.springframework.stereotype.Component;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.pay.WithdrawReqFeign;
import com.onelive.common.model.vo.pay.PayWithdrawResultVO;

/**
 * @ClassName DemoClientFallBack
 * @Desc TODO
 * @Date 2021/3/15 14:41
 */
@Component
public class PayClientFeignBack implements PayClientFeignClient {


    @Override
    public ResultInfo<?> testFeign(String headerMap) {
        return ResultInfo.error();
    }

	@Override
	public ResultInfo<PayWithdrawResultVO> withdraw(WithdrawReqFeign withdrawReqFeign) {
		return ResultInfo.error();
	}
}
    