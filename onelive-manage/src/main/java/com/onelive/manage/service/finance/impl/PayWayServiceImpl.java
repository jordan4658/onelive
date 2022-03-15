package com.onelive.manage.service.finance.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.model.vo.pay.PayWayBackVO;
import com.onelive.common.mybatis.entity.PayWay;
import com.onelive.common.mybatis.mapper.master.pay.PayWayMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayWayMapper;
import com.onelive.manage.service.finance.PayWayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 支付方式 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
@Service
public class PayWayServiceImpl extends ServiceImpl<PayWayMapper, PayWay> implements PayWayService {

    @Resource
    private SlavePayWayMapper slavePayWayMapper;

    @Resource
    private PayWayMapper payWayMapper;


    @Override
    public List<PayWayBackVO> listPage(String payWayName, String payTypeCode,String countryCode, Integer status,Integer providerType) {
        return slavePayWayMapper.listPage(payWayName, payTypeCode,countryCode, status,providerType);
    }

    @Override
    public void add(PayWay payWay) {
        payWayMapper.insert(payWay);
    }

    @Override
    public PayWay selectOne(Long payWayId) {
        QueryWrapper<PayWay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayWay::getPayWayId, payWayId)
                .eq(PayWay::getStatus, PayConstants.StatusEnum.ENABLE.getCode()).last("limit 1 ");
        return slavePayWayMapper.selectOne(queryWrapper);
    }

}
