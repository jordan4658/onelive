package com.onelive.manage.modules.report.business;

import com.onelive.common.constants.business.CommonConstants;
import com.onelive.common.model.dto.report.StatSourceBetDTO;
import com.onelive.common.model.dto.report.StatSourceDTO;
import com.onelive.common.model.vo.report.StatReportVO;
import com.onelive.manage.service.report.ReportStatisticsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lorenzo
 * @Description 统计-报表统计业务类
 * @Date 2021/5/25 9:59
 */
@Component
public class ReportStatisticsBusiness {

    @Resource
    private ReportStatisticsService reportStatisticsService;

    public StatReportVO getReport(Date startTime, Date endTime) {
        StatReportVO vo = new StatReportVO();
        // 充值
        List<StatSourceDTO> recharges = reportStatisticsService.totalRecharge(startTime, endTime);
        for(StatSourceDTO d: recharges) {
            if (CommonConstants.IOS.equals(d.getSource())) {
                vo.setIosRechargeTotal(d.getTotal());
            } else if (CommonConstants.ANDROID.equals(d.getSource())) {
                vo.setAndroidRechargeTotal(d.getTotal());
            }
        }
        // 总充值
        vo.setRechargeTotal(vo.getIosRechargeTotal().add(vo.getAndroidRechargeTotal()));

        // 提现
        List<StatSourceDTO> withdraws = reportStatisticsService.totalWithdraw(startTime, endTime);
        for(StatSourceDTO d: withdraws) {
            if (CommonConstants.IOS.equals(d.getSource())) {
                vo.setIosWithdrawTotal(d.getTotal());
            } else if (CommonConstants.ANDROID.equals(d.getSource())) {
                vo.setAndroidWithdrawTotal(d.getTotal());
            }
        }
        // 总提现
        vo.setWithdrawTotal(vo.getIosWithdrawTotal().add(vo.getAndroidWithdrawTotal()));

//        // 注单报表
//        List<StatSourceBetDTO> betDTOS = reportStatisticsService.queryBetReport(startTime, endTime);
//        for (StatSourceBetDTO d: betDTOS) {
//            if (CommonConstants.IOS.equals(d.getSource())) {
//                // 有效投注额
//                vo.setIosBetTotal(d.getTotal());
//                // 有效注单
//                vo.setIosBetCount(d.getCount());
//            } else if (CommonConstants.ANDROID.equals(d.getSource())) {
//                vo.setAndroidBetTotal(d.getTotal());
//                vo.setAndroidBetCount(d.getCount());
//            }
//        }
//        // 总投注额
//        vo.setBetTotal(vo.getIosBetTotal().add(vo.getAndroidBetTotal()));
//        // 有效注单总数
//        vo.setBetCount(vo.getIosBetCount() + vo.getAndroidBetCount());

        // 充值人数
        int countRecharge = reportStatisticsService.countRechargeUser(startTime, endTime);
        vo.setRechargeCount(countRecharge);
        // 首次充值金额
        BigDecimal firstRecharge = reportStatisticsService.firstRecharge(startTime, endTime);
        vo.setFirstRechargeTotal(firstRecharge);

        // 注册人数
        int registeredUser = reportStatisticsService.countRegisteredUser(startTime, endTime);
        vo.setRegisteredCount(registeredUser);

        return vo;
    }

}
