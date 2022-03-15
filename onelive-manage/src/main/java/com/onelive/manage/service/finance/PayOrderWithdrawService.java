package com.onelive.manage.service.finance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.pay.PayOrderWithdrawBackVO;
import com.onelive.common.mybatis.entity.PayOrderWithdraw;

import java.math.BigDecimal;
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

    List<PayOrderWithdrawBackVO> listPage(String account, Integer orderStatus, String orderNo, Date startDate, Date endDate);

    BigDecimal getWithdrawAmt(Date startTime, Date endTime);
}
