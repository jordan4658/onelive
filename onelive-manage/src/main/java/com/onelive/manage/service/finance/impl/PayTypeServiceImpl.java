package com.onelive.manage.service.finance.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.vo.pay.PayTypeBackVO;
import com.onelive.common.model.vo.pay.PayTypeSelectVO;
import com.onelive.common.mybatis.entity.PayType;
import com.onelive.common.mybatis.mapper.master.pay.PayTypeMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayTypeMapper;
import com.onelive.manage.service.finance.PayTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 支付类型表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
@Service
public class PayTypeServiceImpl extends ServiceImpl<PayTypeMapper, PayType> implements PayTypeService {

    @Resource
    private SlavePayTypeMapper slavePayTypeMapper;

    @Override
    public List<PayTypeBackVO> listPage(String payTypeName) {
        return slavePayTypeMapper.listPage(payTypeName);
    }

    @Override
    public List<PayTypeSelectVO> select() {
        return slavePayTypeMapper.select();
    }
}
