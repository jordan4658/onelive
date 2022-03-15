package com.onelive.common.mybatis.mapper.slave.pay;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.annotation.DataSource;
import com.onelive.common.model.vo.pay.PayTypeBackVO;
import com.onelive.common.model.vo.pay.PayTypeSelectVO;
import com.onelive.common.model.vo.pay.PayTypeVO;
import com.onelive.common.mybatis.entity.PayType;

import java.util.List;

/**
 * <p>
 * 支付类型表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
@DataSource
public interface SlavePayTypeMapper extends BaseMapper<PayType> {

    List<PayTypeVO> getPayTypeList();

    List<PayTypeBackVO> listPage(String payTypeName);

    List<PayTypeSelectVO> select();
}
