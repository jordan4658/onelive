package com.onelive.manage.service.finance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.vo.pay.PayExchangeCurrencyVO;
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

    PageInfo<PayExchangeCurrencyVO> pageList(Integer pageSize, Integer pageNum, String currencyCode);

    Integer selectCount(String currencyCode);

    PayExchangeCurrency selectByCurrencyCode(String localCurrency);
}
