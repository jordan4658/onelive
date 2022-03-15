package com.onelive.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.model.vo.pay.PayWithdrawResultsVO;
import com.onelive.common.mybatis.entity.PayOrderWithdraw;
import com.onelive.common.mybatis.mapper.master.pay.PayOrderWithdrawMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayOrderWithdrawMapper;
import com.onelive.pay.service.PayOrderWithdrawService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<MemGoldchangeVO> withdrawRecordList(Date startDate, Date endDate, String account) {
        return slavePayOrderWithdrawMapper.withdrawRecordList(startDate, endDate, account);
    }

    @Override
    public PayOrderWithdraw getByAccountAndOrderNo(String account, String withdrawOrderNo) {
        QueryWrapper<PayOrderWithdraw> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayOrderWithdraw::getWithdrawNo, withdrawOrderNo)
                .eq(PayOrderWithdraw::getAccount, account).last("limit 1");
        return slavePayOrderWithdrawMapper.selectOne(queryWrapper);
    }

    @Override
    public PayWithdrawResultsVO getWithdrawDetailsByOrderNo(String orderNo, String account) {
        return slavePayOrderWithdrawMapper.getWithdrawDetailsByOrderNo(orderNo,account);
    }

    @Override
    public PayOrderWithdraw getOrderStatus(String account, Integer orderStatus) {
        QueryWrapper<PayOrderWithdraw> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayOrderWithdraw::getWithdrawStatus, orderStatus)
                .eq(PayOrderWithdraw::getAccount, account).last("limit 1");
        return slavePayOrderWithdrawMapper.selectOne(queryWrapper);
    }
}
