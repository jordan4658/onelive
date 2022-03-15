package com.onelive.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.pay.PayBankVO;
import com.onelive.common.mybatis.entity.PayThreeProvider;

import java.util.List;

/**
 * <p>
 * 支付商設置 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
public interface PayThreeProviderService extends IService<PayThreeProvider> {

    List<PayBankVO> getBankList(Integer type);

    PayThreeProvider selectOneById(Long providerId);

    PayThreeProvider getByAgentNo(String  agentNo);

    PayThreeProvider selectOneByAppId(String appId);

    PayThreeProvider getByPayWayId(Long payWayId);

    PayThreeProvider getMerchantCode(String merchantCode);
}
