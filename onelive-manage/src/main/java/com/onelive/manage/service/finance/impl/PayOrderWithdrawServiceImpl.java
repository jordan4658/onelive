package com.onelive.manage.service.finance.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.onelive.common.model.vo.pay.PayOrderWithdrawBackVO;
import com.onelive.common.mybatis.entity.PayOrderWithdraw;
import com.onelive.common.mybatis.mapper.master.pay.PayOrderWithdrawMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayOrderWithdrawMapper;
import com.onelive.manage.service.finance.PayOrderWithdrawService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 提现申请 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
@Service
public class PayOrderWithdrawServiceImpl extends ServiceImpl<PayOrderWithdrawMapper, PayOrderWithdraw> implements PayOrderWithdrawService {

    @Resource
    private SlavePayOrderWithdrawMapper slavePayOrderWithdrawMapper;


    @Override
    public List<PayOrderWithdrawBackVO> listPage(String account, Integer orderStatus, String orderNo, Date startDate, Date endDate) {
        return slavePayOrderWithdrawMapper.listPage(account,orderStatus,orderNo,startDate,endDate);
    }

    @Override
    public BigDecimal getWithdrawAmt(Date startTime, Date endTime) {
        return slavePayOrderWithdrawMapper.getWithdrawAmt(startTime,endTime);
    }
}
