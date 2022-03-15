package com.onelive.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.PayChannel;
import com.onelive.common.mybatis.mapper.master.pay.PayChannelMapper;
import com.onelive.pay.service.PayChannelService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方支付通道 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-30
 */
@Service
public class PayChannelServiceImpl extends ServiceImpl<PayChannelMapper, PayChannel> implements PayChannelService {

}
