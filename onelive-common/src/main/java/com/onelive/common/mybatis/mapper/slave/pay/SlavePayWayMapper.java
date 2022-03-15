package com.onelive.common.mybatis.mapper.slave.pay;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.vo.pay.PayWayBackVO;
import com.onelive.common.model.vo.pay.PayWayVO;
import com.onelive.common.mybatis.entity.PayWay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 支付方式 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
public interface SlavePayWayMapper extends BaseMapper<PayWay> {

    List<PayWayVO> getPayWayList(@Param("countryCode") String countryCode);

    List<PayWayBackVO> listPage(@Param("payWayName") String payWayName,
                                @Param("payTypeCode") String payTypeCode,
                                @Param("countryCode") String  countryCode,
                                @Param("status") Integer status,@Param("providerType")Integer providerType);
}
