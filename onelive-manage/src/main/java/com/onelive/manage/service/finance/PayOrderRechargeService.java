package com.onelive.manage.service.finance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.report.OnlineCompanyDTO;
import com.onelive.common.model.dto.report.OnlineReportDTO;
import com.onelive.common.model.req.report.DepositOnlineReq;
import com.onelive.common.model.vo.pay.OfflinePayOrderRechargeBackVO;
import com.onelive.common.model.vo.pay.OnlinePayOrderRechargeBackVO;
import com.onelive.common.model.vo.report.OnlineAmtAndOfflineAmtVO;
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

    List<OfflinePayOrderRechargeBackVO> offlineListPage(Integer orderType, Integer orderStatus, String account, String orderNo, Date startDate, Date endDate);

    List<OnlinePayOrderRechargeBackVO> onlineListPage(Integer orderType, Integer orderStatus, String account, String orderNo, String providerId, Date startDate, Date endDate);

    Integer updateOrderInfo(PayOrderRecharge payOrderRecharge);

    /**
     * 查询线上线下入款汇总情况
     *
     * @param startDate
     * @param endDate
     * @return
     */
    OnlineCompanyDTO queryOnlineCompanySummery(Date startDate, Date endDate, Integer orderType);

    /**
     * 查询线上支付报表
     *
     * @param req
     * @return
     */
    List<OnlineReportDTO> queryOnlineReportDTOSummery(DepositOnlineReq req);


    /**
     * 根据时间差
     * @param startTime
     * @param endTime
     * @return
     */
    List<OnlineAmtAndOfflineAmtVO> getOnlineAmtAndOfflineAmt(Date startTime, Date endTime);
}
