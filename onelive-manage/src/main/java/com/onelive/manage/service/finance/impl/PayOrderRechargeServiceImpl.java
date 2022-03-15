package com.onelive.manage.service.finance.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.dto.report.OnlineCompanyDTO;
import com.onelive.common.model.dto.report.OnlineReportDTO;
import com.onelive.common.model.req.report.DepositOnlineReq;
import com.onelive.common.model.vo.pay.OfflinePayOrderRechargeBackVO;
import com.onelive.common.model.vo.pay.OnlinePayOrderRechargeBackVO;
import com.onelive.common.model.vo.report.OnlineAmtAndOfflineAmtVO;
import com.onelive.common.mybatis.entity.PayOrderRecharge;
import com.onelive.common.mybatis.mapper.master.pay.PayOrderRechargeMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayOrderRechargeMapper;
import com.onelive.manage.service.finance.PayOrderRechargeService;
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
    public List<OfflinePayOrderRechargeBackVO> offlineListPage(Integer orderType, Integer orderStatus, String account, String orderNo, Date startDate, Date endDate) {
        return slavePayOrderRechargeMapper.offlineListPage(orderType, orderStatus, account, orderNo, startDate, endDate);
    }

    @Override
    public List<OnlinePayOrderRechargeBackVO> onlineListPage(Integer orderType, Integer orderStatus, String account, String orderNo,
                                                             String providerId, Date startDate, Date endDate) {
        return slavePayOrderRechargeMapper.onlineListPage(orderType, orderStatus, account, orderNo, providerId, startDate, endDate);
    }

    @Override
    public Integer updateOrderInfo(PayOrderRecharge payOrderRecharge) {
        Integer num = payOrderRechargeMapper.updateById(payOrderRecharge);
        return num;
    }

    @Override
    public OnlineCompanyDTO queryOnlineCompanySummery(Date startDate, Date endDate, Integer orderType) {
        return slavePayOrderRechargeMapper.queryOnlineCompanySummery(startDate, endDate, orderType);
    }

    @Override
    public List<OnlineReportDTO> queryOnlineReportDTOSummery(DepositOnlineReq req) {
        return slavePayOrderRechargeMapper.queryOnlineReportDTOSummery(req);
    }

    @Override
    public List<OnlineAmtAndOfflineAmtVO> getOnlineAmtAndOfflineAmt(Date startTime, Date endTime) {
        return slavePayOrderRechargeMapper.getOnlineAmtAndOfflineAmt(startTime,endTime);
    }
}
