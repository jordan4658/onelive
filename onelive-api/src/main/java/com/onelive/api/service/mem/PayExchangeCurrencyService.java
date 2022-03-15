package com.onelive.api.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.PayExchangeCurrency;

/**
 * <p>
 * 货币国际汇率 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
public interface PayExchangeCurrencyService extends IService<PayExchangeCurrency> {

    PayExchangeCurrency selectByCurrencyCode(String currencyCode);
}
