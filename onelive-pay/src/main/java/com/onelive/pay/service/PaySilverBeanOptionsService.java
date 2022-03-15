package com.onelive.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.PaySilverBeanOptions;

/**
 * <p>
 * 提现快捷选项配置表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-07
 */
public interface PaySilverBeanOptionsService extends IService<PaySilverBeanOptions> {

    PaySilverBeanOptions getByLang(String lang);
}
