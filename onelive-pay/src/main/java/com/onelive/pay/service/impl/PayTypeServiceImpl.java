package com.onelive.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.vo.pay.PayTypeVO;
import com.onelive.common.mybatis.entity.PayType;
import com.onelive.common.mybatis.mapper.master.pay.PayTypeMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayTypeMapper;
import com.onelive.pay.service.PayTypeService;
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
    public List<PayTypeVO> getPayTypeList() {
        return slavePayTypeMapper.getPayTypeList();
    }
}
