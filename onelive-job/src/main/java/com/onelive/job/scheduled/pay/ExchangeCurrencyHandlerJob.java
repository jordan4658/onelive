package com.onelive.job.scheduled.pay;

import com.onelive.job.scheduled.pay.business.ExchangeCurrencyBusiness;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class ExchangeCurrencyHandlerJob {

    @Autowired
    private ExchangeCurrencyBusiness exchangeCurrencyBusiness;

    /**
     * 获取汇率转换数据，并入库保存
     *
     * @return
     */
    @XxlJob("exchangeCurrencyHandler")
    public ReturnT<String> exchangeCurrencyHandlerJob(String str) {
        exchangeCurrencyBusiness.getExchangeCurrencyHandlerJob();
        return  ReturnT.SUCCESS;
    }

    public static void main(String[] args) {
        BigDecimal a=new BigDecimal("0.000043").multiply(new BigDecimal("97").divide(new BigDecimal(100)));
        System.out.println(a);


//        //获取兑换的汇率列表
//        List<String> currencyList = new ArrayList<>();
//        currencyList.add("CNY");
//        currencyList.add("VND");
//
//        //获取聚合数据查询汇率的key列表（每个key每天只能请求50次请求）
//        List<String> keyList = new ArrayList<>();
//        keyList.add("8fa912284f207eea1b609df2113224cf");
//        keyList.add("8fa912284f207eea1b609df2113224cf");
//
//        String url = "http://op.juhe.cn/onebox/exchange/currency";
//
//        for (String currency : currencyList) {
//            //每个key一天只能请求50次
//            for (String key : keyList) {
//                Map<String, String> param = new HashMap<>();
//                param.put("key", key);
//                param.put("from", currency);
//                param.put("to", "USD");
//                String resultStr = HttpClient_Fh_Util.doGetRequest(url, null, null, param);
//                if (StringUtils.isEmpty(resultStr)) {
//                    log.info("url:{},执行Http请求失败", url);
//                    continue;
//                }
//                JSONObject json = JSONObject.parseObject(resultStr);
//                if (Integer.valueOf(json.get("error_code") + "").equals(112)) {
//                    log.info("USD兑换->{}币种汇率====失败原因：{}", currency, json.get("reason"));
//                    continue;
//                } else if (!Integer.valueOf(json.get("error_code") + "").equals(0)) {
//                    log.info("USD兑换->{}币种汇率====失败原因：{}", currency, json.get("reason"));
//                } else {
//                    //获取兑换汇率成功处理
//                    log.info("USD兑换->{}币种汇率====成功!", currency);
//                    List<ExchangeCurrencyVO> list = JSONArray.parseArray(json.get("result").toString(), ExchangeCurrencyVO.class);
//                }
//
//            }
//        }

    }
}
