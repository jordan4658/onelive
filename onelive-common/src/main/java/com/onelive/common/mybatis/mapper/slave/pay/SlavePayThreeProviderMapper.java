package com.onelive.common.mybatis.mapper.slave.pay;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.vo.pay.PayBankVO;
import com.onelive.common.model.vo.pay.PayThreeProviderBackVO;
import com.onelive.common.model.vo.pay.PayThreeProviderSelectVO;
import com.onelive.common.mybatis.entity.PayThreeProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 支付商設置 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
public interface SlavePayThreeProviderMapper extends BaseMapper<PayThreeProvider> {

    List<PayBankVO> getBankList(Integer type);

    List<PayThreeProviderBackVO> listPage(@Param("providerName") String providerName, @Param("providerType") Integer providerType);

    List<PayThreeProviderSelectVO> selectProviderList(@Param("providerType")Integer providerType);


    PayThreeProvider getByPayWayId(@Param("payWayId") Long payWayId);
}
