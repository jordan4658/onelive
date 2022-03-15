package com.onelive.manage.service.finance.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.vo.pay.PayThreeProviderBackVO;
import com.onelive.common.model.vo.pay.PayThreeProviderSelectVO;
import com.onelive.common.mybatis.entity.PayThreeProvider;
import com.onelive.common.mybatis.mapper.master.pay.PayThreeProviderMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayThreeProviderMapper;
import com.onelive.manage.service.finance.PayThreeProviderService;
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
    public List<PayThreeProviderBackVO> listPage(String providerName, Integer providerType) {
        return slavePayThreeProviderMapper.listPage(providerName,providerType);
    }

    @Override
    public List<PayThreeProviderSelectVO> selectList(Integer providerType) {
        return slavePayThreeProviderMapper.selectProviderList(providerType);
    }
}
