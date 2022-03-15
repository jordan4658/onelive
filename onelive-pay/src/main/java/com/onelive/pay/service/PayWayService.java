package com.onelive.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.pay.PayWayVO;
import com.onelive.common.mybatis.entity.PayWay;

import java.util.List;

/**
 * <p>
 * 支付方式 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
public interface PayWayService extends IService<PayWay> {

    List<PayWayVO> getPayWayList(String countryCode);

    PayWay selectOne(Long payWayId);
}
