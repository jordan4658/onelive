package com.onelive.manage.service.finance.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.index.LiveColumnDto;
import com.onelive.common.model.req.pay.PayExchangeRateCfgAddReq;
import com.onelive.common.model.vo.pay.PayExchangeRateCfgVO;
import com.onelive.common.mybatis.entity.GameModule;
import com.onelive.common.mybatis.entity.LiveColumn;
import com.onelive.common.mybatis.entity.PayExchangeCurrency;
import com.onelive.common.mybatis.entity.PayExchangeRateCfg;
import com.onelive.common.mybatis.mapper.master.pay.PayExchangeRateCfgMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayExchangeRateCfgMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.finance.PayExchangeRateCfgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 汇率配置 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-07
 */
@Service
public class PayExchangeRateCfgServiceImpl extends ServiceImpl<PayExchangeRateCfgMapper, PayExchangeRateCfg> implements PayExchangeRateCfgService {

    @Resource
    private SlavePayExchangeRateCfgMapper slavePayExchangeRateCfgMapper;

    @Override
    public PageInfo<PayExchangeRateCfgVO> pageList(Integer pageSize, Integer pageNum, String currencyCode) {
        QueryWrapper<PayExchangeRateCfg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayExchangeRateCfg::getIsDelete, false);
        if (StringUtils.isNotEmpty(currencyCode)) {
            queryWrapper.lambda().eq(PayExchangeRateCfg::getCurrencyCode, currencyCode);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<PayExchangeRateCfg> list = slavePayExchangeRateCfgMapper.selectList(queryWrapper);
        return new PageInfo<>(BeanCopyUtil.copyCollection(list, PayExchangeRateCfgVO.class));
    }

    @Override
    public void save(PayExchangeRateCfgAddReq req) {

    }

    @Override
    public Integer selectCount(String currencyCode) {
        QueryWrapper<PayExchangeRateCfg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayExchangeRateCfg::getIsDelete, false);
        queryWrapper.lambda().eq(PayExchangeRateCfg::getCurrencyCode, currencyCode);
        return slavePayExchangeRateCfgMapper.selectCount(queryWrapper);
    }

    @Override
    public PayExchangeRateCfg getByCurrencyCode(String currencyCode) {
        QueryWrapper<PayExchangeRateCfg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayExchangeRateCfg::getIsDelete, false);
        queryWrapper.lambda().eq(PayExchangeRateCfg::getCurrencyCode, currencyCode);
        return slavePayExchangeRateCfgMapper.selectOne(queryWrapper);
    }
}
