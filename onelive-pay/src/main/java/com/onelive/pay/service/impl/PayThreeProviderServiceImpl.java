package com.onelive.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.vo.pay.PayBankVO;
import com.onelive.common.mybatis.entity.PayThreeProvider;
import com.onelive.common.mybatis.mapper.master.pay.PayThreeProviderMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayThreeProviderMapper;
import com.onelive.pay.service.PayThreeProviderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 支付商設置 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
@Service
public class PayThreeProviderServiceImpl extends ServiceImpl<PayThreeProviderMapper, PayThreeProvider> implements PayThreeProviderService {

    @Resource
    private SlavePayThreeProviderMapper slavePayThreeProviderMapper;


    @Override
    public List<PayBankVO> getBankList(Integer type) {
        return slavePayThreeProviderMapper.getBankList(type);
    }

    @Override
    public PayThreeProvider selectOneById(Long providerId) {
        QueryWrapper<PayThreeProvider> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayThreeProvider::getProviderId, providerId).eq(PayThreeProvider::getIsDelete, false).last("limit 1 ");
        return slavePayThreeProviderMapper.selectOne(queryWrapper);
    }

    @Override
    public PayThreeProvider selectOneByAppId(String providerAppId) {
        QueryWrapper<PayThreeProvider> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayThreeProvider::getProviderAppId, providerAppId).eq(PayThreeProvider::getIsDelete, false).last("limit 1 ");
        return slavePayThreeProviderMapper.selectOne(queryWrapper);
    }

    @Override
    public PayThreeProvider getByPayWayId(Long payWayId) {
        return slavePayThreeProviderMapper.getByPayWayId(payWayId);
    }

    @Override
    public PayThreeProvider getMerchantCode(String merchantCode) {
        QueryWrapper<PayThreeProvider> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(PayThreeProvider::getProviderCode, merchantCode).eq(PayThreeProvider::getIsDelete, false)
                .last("limit 1 ");
        return slavePayThreeProviderMapper.selectOne(queryWrapper);
    }

    @Override
    public PayThreeProvider getByAgentNo(String agentNo) {
        QueryWrapper<PayThreeProvider> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(PayThreeProvider::getAgentNo, agentNo).last("limit 1 ");
        return slavePayThreeProviderMapper.selectOne(queryWrapper);
    }
}
