package com.onelive.manage.service.finance.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.PayMentInfo;
import com.onelive.common.mybatis.mapper.master.pay.PayMentInfoMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayMentInfoMapper;
import com.onelive.manage.service.finance.PayMentInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 支付信息 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
@Service
public class PayMentInfoServiceImpl extends ServiceImpl<PayMentInfoMapper, PayMentInfo> implements PayMentInfoService {

    @Resource
    private SlavePayMentInfoMapper slavePayMentInfoMapper;

}
