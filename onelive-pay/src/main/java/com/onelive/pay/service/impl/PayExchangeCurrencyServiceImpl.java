package com.onelive.pay.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.vo.pay.PayExchangeCurrencyVO;
import com.onelive.common.mybatis.entity.PayExchangeCurrency;
import com.onelive.common.mybatis.mapper.master.pay.PayExchangeCurrencyMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayExchangeCurrencyMapper;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.pay.service.PayExchangeCurrencyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 货币国际汇率 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
@Service
public class PayExchangeCurrencyServiceImpl extends ServiceImpl<PayExchangeCurrencyMapper, PayExchangeCurrency> implements PayExchangeCurrencyService {

    @Resource
    private SlavePayExchangeCurrencyMapper slavePayExchangeCurrencyMapper;

    @Override
    public PayExchangeCurrency selectByCurrencyCode(String currencyCode) {
        QueryWrapper<PayExchangeCurrency> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayExchangeCurrency::getIsDelete, false);
        queryWrapper.lambda().eq(PayExchangeCurrency::getCurrencyCode, currencyCode);
        return slavePayExchangeCurrencyMapper.selectOne(queryWrapper);
    }
}
