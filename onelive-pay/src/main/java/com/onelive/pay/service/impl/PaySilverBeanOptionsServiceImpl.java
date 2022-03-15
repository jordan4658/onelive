package com.onelive.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.PaySilverBeanOptions;
import com.onelive.common.mybatis.mapper.master.pay.PaySilverBeanOptionsMapper;
import com.onelive.pay.service.PaySilverBeanOptionsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 提现快捷选项配置表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-07
 */
@Service
public class PaySilverBeanOptionsServiceImpl extends ServiceImpl<PaySilverBeanOptionsMapper, PaySilverBeanOptions> implements PaySilverBeanOptionsService {

    @Override
    public PaySilverBeanOptions getByLang(String lang) {
        return null;
    }
}
