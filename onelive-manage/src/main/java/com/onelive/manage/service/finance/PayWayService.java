package com.onelive.manage.service.finance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.pay.PayWayBackVO;
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


    List<PayWayBackVO> listPage(String payWayName, String payTypeCode,String countryCode, Integer status,Integer providerType);

    void add(PayWay payWay);

    PayWay selectOne(Long payWayId);
}
