package com.onelive.job.service.pay.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.mybatis.entity.PayExchangeCurrency;
import com.onelive.common.mybatis.entity.PayExchangeRateCfg;
import com.onelive.common.mybatis.entity.PayFindExchangeCfg;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.mybatis.mapper.master.pay.PayExchangeCurrencyMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayExchangeCurrencyMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayExchangeRateCfgMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayFindExchangeCfgMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysCountryMapper;
import com.onelive.job.service.pay.ExchangeCurrencyJobService;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class ExchangeCurrencyJobServiceImpl implements ExchangeCurrencyJobService {

    @Resource
    private SlavePayExchangeCurrencyMapper slavePayExchangeCurrencyMapper;
    @Resource
    private PayExchangeCurrencyMapper payExchangeCurrencyMapper;
    @Resource
    private SlavePayExchangeRateCfgMapper slavePayExchangeRateCfgMapper;
    @Resource
    private SlavePayFindExchangeCfgMapper slavePayFindExchangeCfgMapper;
    @Resource
    private SlaveSysCountryMapper slaveSysCountryMapper;


    @Override
    public List<SysCountry> getAllCountryList() {
        QueryWrapper<SysCountry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysCountry::getIsFrozen, false);
        return slaveSysCountryMapper.selectList(queryWrapper);
    }

    @Override
    public List<PayExchangeRateCfg> getExchangeRateCfgList() {
        QueryWrapper<PayExchangeRateCfg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayExchangeRateCfg::getIsDelete, false);
        return slavePayExchangeRateCfgMapper.selectList(queryWrapper);
    }

    @Override
    public List<PayFindExchangeCfg> getFindExchangeCfgList() {
        QueryWrapper<PayFindExchangeCfg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayFindExchangeCfg::getIsDelete, false);
        return slavePayFindExchangeCfgMapper.selectList(queryWrapper);
    }

    @Override
    public PayExchangeCurrency getByCurrencyCode(String localCurrency) {
        QueryWrapper<PayExchangeCurrency> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayExchangeCurrency::getIsDelete, false);
        queryWrapper.lambda().eq(PayExchangeCurrency::getCurrencyCode, localCurrency);
        return slavePayExchangeCurrencyMapper.selectOne(queryWrapper);
    }

    @Override
    public void saveExchangeCurrency(PayExchangeCurrency payExchangeCurrency) {
        payExchangeCurrencyMapper.insert(payExchangeCurrency);
    }

    @Override
    public void updateExchangeCurrency(PayExchangeCurrency payExchangeCurrency) {
        payExchangeCurrencyMapper.updateById(payExchangeCurrency);
    }
}
