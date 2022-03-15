package com.onelive.job.scheduled.pay.business;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onelive.common.model.vo.pay.ExchangeCurrencyVO;
import com.onelive.common.mybatis.entity.PayExchangeCurrency;
import com.onelive.common.mybatis.entity.PayExchangeRateCfg;
import com.onelive.common.mybatis.entity.PayFindExchangeCfg;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.utils.http.HttpClient_Fh_Util;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.job.service.pay.ExchangeCurrencyJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExchangeCurrencyBusiness {

    @Autowired
    private ExchangeCurrencyJobService exchangeCurrencyJobService;

    public void getExchangeCurrencyHandlerJob() {

        //获取需要查询汇率国家集合
        List<SysCountry> currencyList = exchangeCurrencyJobService.getAllCountryList();
        //获取国家对应的汇率配置信息集合
        List<PayExchangeRateCfg> exchangeRateList = exchangeCurrencyJobService.getExchangeRateCfgList();
        //获取汇率查询key配置集合
        List<PayFindExchangeCfg> keyList = exchangeCurrencyJobService.getFindExchangeCfgList();
        for (SysCountry currency : currencyList) {
            //每个key一天只能请求50次
            for (PayFindExchangeCfg key : keyList) {
                Map<String, String> param = new HashMap<>();
                param.put("key", key.getExchangeKey());
//                param.put("from", currency.getLocalCurrency());
                param.put("from", "USD");
                param.put("to", currency.getLocalCurrency());
                String resultStr = HttpClient_Fh_Util.doGetRequest(key.getExchangeUrl(), null, null, param);
                if (StringUtils.isEmpty(resultStr)) {
                    log.info("url:{},执行Http请求失败", key.getExchangeUrl());
                    continue;
                }
                JSONObject json = JSONObject.parseObject(resultStr);
                if (Integer.valueOf(json.get("error_code") + "").equals(112)) {
                    log.info("USD兑换->{}币种汇率====失败原因：{}", currency.getLocalCurrency(), json.get("reason"));
                    continue;
                } else if (!Integer.valueOf(json.get("error_code") + "").equals(0)) {
                    log.info("USD兑换->{}币种汇率====失败原因：{}", currency.getLocalCurrency(), json.get("reason"));
                } else {
                    //获取兑换汇率成功处理
                    log.info("USD兑换->{}币种汇率====成功!", currency.getLocalCurrency());
                    List<ExchangeCurrencyVO> list = JSONArray.parseArray(json.get("result").toString(), ExchangeCurrencyVO.class);
                    //根据汇率配置信息 计算充值、提现汇率
                    for (PayExchangeRateCfg cfg : exchangeRateList) {
                        if (cfg.getCurrencyCode().equals(currency.getLocalCurrency())) {
                            PayExchangeCurrency payExchangeCurrency = exchangeCurrencyJobService.getByCurrencyCode(currency.getLocalCurrency());
                            if (payExchangeCurrency == null) {
                                payExchangeCurrency = new PayExchangeCurrency();
                            }
                            BeanUtils.copyProperties(list.get(0), payExchangeCurrency);
                            payExchangeCurrency.setCurrencyFName(list.get(0).getCurrencyF_Name());
                            payExchangeCurrency.setCurrencyTName(list.get(0).getCurrencyT_Name());
                            BigDecimal percentage=new BigDecimal(100);
                            //计算充值（转换汇率）
                            BigDecimal czFloatingValue = new BigDecimal(cfg.getCzFloatingValue());
                            BigDecimal czReality = czFloatingValue.add(percentage);
                            BigDecimal czExchange = new BigDecimal(payExchangeCurrency.getExchange()).multiply(czReality.divide(percentage));
                            payExchangeCurrency.setCzExchange(czExchange.toString());
                            //计算提现汇率
                            BigDecimal txFloatingValue = new BigDecimal(cfg.getTxFloatingValue());
                            BigDecimal txReality = txFloatingValue.add(percentage);
                            BigDecimal txExchange = new BigDecimal(payExchangeCurrency.getExchange()).multiply(txReality.divide(percentage));
                            payExchangeCurrency.setTxExchange(txExchange.toString());
                            payExchangeCurrency.setUpdateUser("sys");
                            Date date=new Date();
                            payExchangeCurrency.setUpdateTime(date);
                            payExchangeCurrency.setCurrencyCode(currency.getLocalCurrency());
                            //新增或修改 货币信息
                            if(payExchangeCurrency.getExchangeCurrencyId()==null){
                                payExchangeCurrency.setCreateUser("sys");
                                payExchangeCurrency.setCreateTime(date);
                                payExchangeCurrency.setIsDelete(false);
                                exchangeCurrencyJobService.saveExchangeCurrency(payExchangeCurrency);
                            }else{
                                exchangeCurrencyJobService.updateExchangeCurrency(payExchangeCurrency);
                            }
                            break;
                        }

                    }
                }

            }
        }


    }
}
