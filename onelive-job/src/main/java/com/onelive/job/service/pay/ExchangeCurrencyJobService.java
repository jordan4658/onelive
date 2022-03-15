package com.onelive.job.service.pay;

import com.onelive.common.mybatis.entity.PayExchangeCurrency;
import com.onelive.common.mybatis.entity.PayExchangeRateCfg;
import com.onelive.common.mybatis.entity.PayFindExchangeCfg;
import com.onelive.common.mybatis.entity.SysCountry;

import java.util.List;

public interface ExchangeCurrencyJobService  {


    List<SysCountry> getAllCountryList();

    List<PayExchangeRateCfg> getExchangeRateCfgList();

    List<PayFindExchangeCfg> getFindExchangeCfgList();

    PayExchangeCurrency getByCurrencyCode(String localCurrency);

    void saveExchangeCurrency(PayExchangeCurrency payExchangeCurrency);

    void updateExchangeCurrency(PayExchangeCurrency payExchangeCurrency);
}
