package com.onelive.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.pay.PayTypeVO;
import com.onelive.common.mybatis.entity.PayType;

import java.util.List;

/**
 * <p>
 * 支付类型表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
public interface PayTypeService extends IService<PayType> {

    List<PayTypeVO> getPayTypeList();
}
