package com.onelive.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.PayTransactionTypeCfg;
import com.onelive.common.mybatis.mapper.master.pay.PayTransactionTypeCfgMapper;
import com.onelive.pay.service.PayTransactionTypeCfgService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 查询交易类型配置表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-14
 */
@Service
public class PayTransactionTypeCfgServiceImpl extends ServiceImpl<PayTransactionTypeCfgMapper, PayTransactionTypeCfg> implements PayTransactionTypeCfgService {

}
