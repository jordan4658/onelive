package com.onelive.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.mybatis.entity.PayOrderRecharge;
import com.onelive.common.mybatis.mapper.master.pay.PayOrderRechargeMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayOrderRechargeMapper;
import com.onelive.pay.service.PayOrderRechargeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单信息主 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
@Service
public class PayOrderRechargeServiceImpl extends ServiceImpl<PayOrderRechargeMapper, PayOrderRecharge> implements PayOrderRechargeService {

    @Resource
    private PayOrderRechargeMapper payOrderRechargeMapper;
    @Resource
    private SlavePayOrderRechargeMapper slavePayOrderRechargeMapper;

    @Override
    public void insertOrderInfo(PayOrderRecharge payOrderRecharge) {
        payOrderRechargeMapper.insert(payOrderRecharge);

    }

    @Override
    public PayOrderRecharge getByOrderNo(String orderNo) {
        QueryWrapper<PayOrderRecharge> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayOrderRecharge::getOrderNo, orderNo).last("limit 1 ");
        return slavePayOrderRechargeMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer updateOrderInfo(PayOrderRecharge payOrderRecharge) {
        Integer num = payOrderRechargeMapper.updateById(payOrderRecharge);
        return num;
    }

    @Override
    public List<MemGoldchangeVO> rechargeRecordList(Date startDate, Date endDate, String account) {
        return slavePayOrderRechargeMapper.rechargeRecordList(startDate, endDate, account);
    }

    @Override
    public MemGoldchangeVO getByAccountAndOrderNo(String account, String orderNo) {
        return slavePayOrderRechargeMapper.getByAccountAndOrderNo(account, orderNo);
    }

    @Override
    public void updateByOrderNo(String orderNo, Integer orderStatus, AppLoginUser user) {
        UpdateWrapper<PayOrderRecharge> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(orderStatus != null, PayOrderRecharge::getOrderStatus, orderStatus)
                .set(PayOrderRecharge::getUpdateUser,user.getUserAccount())
                .set(PayOrderRecharge::getUpdateTime,new Date())
                .eq(StringUtils.isNotBlank(orderNo), PayOrderRecharge::getOrderNo, orderNo);
        this.update(updateWrapper);
    }

}
