package com.onelive.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.mybatis.entity.PayOrderRecharge;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单信息主 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
public interface PayOrderRechargeService extends IService<PayOrderRecharge> {

    void insertOrderInfo(PayOrderRecharge payOrderRecharge);

    PayOrderRecharge getByOrderNo(String orderNo);

    Integer updateOrderInfo(PayOrderRecharge payOrderRecharge);

    List<MemGoldchangeVO> rechargeRecordList(Date startDate, Date endDate, String account);

    MemGoldchangeVO getByAccountAndOrderNo(String account, String orderNo);

    void updateByOrderNo(String orderNo,Integer orderStatus, AppLoginUser user);
}
