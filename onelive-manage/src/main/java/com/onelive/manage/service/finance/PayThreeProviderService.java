package com.onelive.manage.service.finance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.pay.PayThreeProviderBackVO;
import com.onelive.common.model.vo.pay.PayThreeProviderSelectVO;
import com.onelive.common.mybatis.entity.PayThreeProvider;

import java.util.List;

/**
 * <p>
 * 支付商設置 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
public interface PayThreeProviderService extends IService<PayThreeProvider> {


    List<PayThreeProviderBackVO> listPage(String providerName, Integer providerType);

    List<PayThreeProviderSelectVO> selectList(Integer providerType);
}
