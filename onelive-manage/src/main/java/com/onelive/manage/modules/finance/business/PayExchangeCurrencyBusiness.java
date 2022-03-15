package com.onelive.manage.modules.finance.business;


import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.PayExchangeCurrencyAddReq;
import com.onelive.common.model.req.pay.PayExchangeCurrencyUpdateReq;
import com.onelive.common.model.vo.pay.PayExchangeCurrencyVO;
import com.onelive.common.mybatis.entity.PayExchangeCurrency;
import com.onelive.common.mybatis.entity.PayExchangeRateCfg;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.finance.PayExchangeCurrencyService;
import com.onelive.manage.service.finance.PayExchangeRateCfgService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PayExchangeCurrencyBusiness {

    @Resource
    private PayExchangeCurrencyService payExchangeCurrencyService;
    @Resource
    private PayExchangeRateCfgService payExchangeRateCfgService;

    /**
     * 查询汇率配置列表信息
     *
     * @param pageSize
     * @param pageNum
     * @param currencyCode
     * @return
     */
    public PageInfo<PayExchangeCurrencyVO> pageList(Integer pageSize, Integer pageNum, String currencyCode) {
        return payExchangeCurrencyService.pageList(pageSize, pageNum, currencyCode);
    }


    public void save(PayExchangeCurrencyAddReq req, LoginUser loginAdmin) {
        if (StringUtils.isEmpty(req.getCurrencyCode())) {
            throw new BusinessException("国家编码不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyF())) {
            throw new BusinessException("转换前的货币代码不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyFName())) {
            throw new BusinessException("转换前的货币名称不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyT())) {
            throw new BusinessException("转换成的货币代码不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyTName())) {
            throw new BusinessException("转换成的货币名称不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyFD())) {
            throw new BusinessException("转换金额不能为空！");
        }
        if (StringUtils.isEmpty(req.getExchange())) {
            throw new BusinessException("当前汇率不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyFD())) {
            throw new BusinessException("转换金额不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyUnit())) {
            throw new BusinessException("兑换币种单位不能为空！");
        }
        //检查 国家编码是否已存在!
        Integer count = payExchangeCurrencyService.selectCount(req.getCurrencyCode());
        if (count > 0) {
            throw new BusinessException("国家编码已存在，请勿重复添加！");
        }
        PayExchangeCurrency currency = new PayExchangeCurrency();
        Date date = new Date();
        BeanUtils.copyProperties(req, currency);
        currency.setCreateTime(date);
        if (currency.getUpdateTime() == null) {
            currency.setUpdateTime(date);
        }
        currency.setCreateUser(loginAdmin.getAccLogin());
        currency.setUpdateUser(loginAdmin.getAccLogin());
        currency.setIsDelete(false);
        calculateExchangeRate(currency);
        payExchangeCurrencyService.save(currency);
    }

    public void update(PayExchangeCurrencyUpdateReq req, LoginUser loginAdmin) {
        if (req.getExchangeCurrencyId() == null) {
            throw new BusinessException("汇率ID不能为空！！");
        }
        if (StringUtils.isEmpty(req.getCurrencyCode())) {
            throw new BusinessException("国家编码不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyF())) {
            throw new BusinessException("转换前的货币代码不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyFName())) {
            throw new BusinessException("转换前的货币名称不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyT())) {
            throw new BusinessException("转换成的货币代码不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyTName())) {
            throw new BusinessException("转换成的货币名称不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyFD())) {
            throw new BusinessException("转换金额不能为空！");
        }
        if (StringUtils.isEmpty(req.getExchange())) {
            throw new BusinessException("当前汇率不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyFD())) {
            throw new BusinessException("转换金额不能为空！");
        }
        if (StringUtils.isEmpty(req.getCurrencyUnit())) {
            throw new BusinessException("兑换币种单位不能为空！");
        }
        PayExchangeCurrency currency = payExchangeCurrencyService.getById(req.getExchangeCurrencyId());
        if (currency == null) {
            throw new BusinessException("更新的信息不存在！");
        }
        if (!currency.getCurrencyCode().equals(req.getCurrencyCode())) {
            //检查 国家编码是否已存在!
            Integer count = payExchangeCurrencyService.selectCount(req.getCurrencyCode());
            if (count > 0) {
                throw new BusinessException("国家编码已存在，请勿重复添加！");
            }
        }
        Date date = new Date();
        BeanUtils.copyProperties(req, currency);
        currency.setUpdateUser(loginAdmin.getAccLogin());
        currency.setUpdateTime(date);
        calculateExchangeRate(currency);
        payExchangeCurrencyService.updateById(currency);
    }

    public void delete(List<Long> ids, LoginUser loginAdmin) {
        for (Long id : ids) {
            PayExchangeCurrency currency = payExchangeCurrencyService.getById(id);
            if (currency != null) {
                currency.setIsDelete(true);
                currency.setUpdateUser(loginAdmin.getAccLogin());
                currency.setUpdateTime(new Date());
                payExchangeCurrencyService.updateById(currency);
            }
        }

    }


    private void calculateExchangeRate(PayExchangeCurrency currency) {
        PayExchangeRateCfg cfg = payExchangeRateCfgService.getByCurrencyCode(currency.getCurrencyCode());
        if(cfg==null){
            throw new BusinessException("国家货币"+currency.getCurrencyCode()+" 未查询到 汇率配置信息！");
        }
        BigDecimal percentage=new BigDecimal(100);
        //计算充值（转换汇率）
        BigDecimal czFloatingValue = new BigDecimal(cfg.getCzFloatingValue());
        BigDecimal czReality = czFloatingValue.add(percentage);
        BigDecimal czExchange = new BigDecimal(currency.getExchange()).multiply(czReality.divide(percentage));
        currency.setCzExchange(czExchange.toString());
        //计算提现汇率
        BigDecimal txFloatingValue = new BigDecimal(cfg.getTxFloatingValue());
        BigDecimal txReality = txFloatingValue.add(percentage);
        BigDecimal txExchange = new BigDecimal(currency.getExchange()).multiply(txReality.divide(percentage));
        currency.setTxExchange(txExchange.toString());
    }
}
