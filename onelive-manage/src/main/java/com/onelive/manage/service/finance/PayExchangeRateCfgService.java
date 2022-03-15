package com.onelive.manage.service.finance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.pay.PayExchangeRateCfgAddReq;
import com.onelive.common.model.vo.pay.PayExchangeRateCfgVO;
import com.onelive.common.mybatis.entity.PayExchangeRateCfg;

/**
 * <p>
 * 汇率配置 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
public interface PayExchangeRateCfgService extends IService<PayExchangeRateCfg> {

    PageInfo<PayExchangeRateCfgVO> pageList(Integer pageSize, Integer pageNum,String currencyCode);

    void save(PayExchangeRateCfgAddReq req);

    Integer selectCount(String currencyCode);

    PayExchangeRateCfg getByCurrencyCode(String currencyCode);
}
