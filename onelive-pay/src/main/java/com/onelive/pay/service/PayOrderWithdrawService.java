package com.onelive.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.model.vo.pay.PayWithdrawResultsVO;
import com.onelive.common.mybatis.entity.PayOrderWithdraw;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 提现申请 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
public interface PayOrderWithdrawService extends IService<PayOrderWithdraw> {

    List<MemGoldchangeVO> withdrawRecordList(Date startDate, Date endDate, String account);

    PayOrderWithdraw getByAccountAndOrderNo(String account, String withdrawOrderNo);

    PayWithdrawResultsVO getWithdrawDetailsByOrderNo(String orderNo, String account);

    PayOrderWithdraw getOrderStatus(String account, Integer orderStatus);
}
