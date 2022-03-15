package com.onelive.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.model.vo.pay.PayWayVO;
import com.onelive.common.mybatis.entity.PayWay;
import com.onelive.common.mybatis.mapper.master.pay.PayWayMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayWayMapper;
import com.onelive.pay.service.PayWayService;
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


    @Override
    public List<PayWayVO> getPayWayList(String countryCode) {

        return slavePayWayMapper.getPayWayList(countryCode);
    }

    @Override
    public PayWay selectOne(Long payWayId) {
        QueryWrapper<PayWay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayWay::getPayWayId, payWayId)
                .eq(PayWay::getStatus, PayConstants.StatusEnum.ENABLE.getCode()).last("limit 1 ");
        return slavePayWayMapper.selectOne(queryWrapper);
    }
}
