package com.onelive.manage.modules.report.business;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.report.FundsImOrderDTO;
import com.onelive.common.model.dto.report.FundsRechargeDTO;
import com.onelive.common.model.dto.report.FundsWithdrawDTO;
import com.onelive.common.model.req.report.FundsReportReq;
import com.onelive.common.model.vo.report.FundsReportListVO;
import com.onelive.common.model.vo.report.FundsReportSummaryVO;
import com.onelive.common.model.vo.report.FundsReportVO;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayOrderRechargeMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayOrderWithdrawMapper;
import com.onelive.common.utils.others.DateInnerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName FundsReportBusiness
 * @Desc 资金报表业务类
 */
@Component
@Slf4j
public class FundsReportBusiness {

    public static final int EXP_FUND_STATISTICS_DAYS = 59;
    @Resource
    private SlavePayOrderRechargeMapper slavePayOrderRechargeMapper;
    @Resource
    private SlavePayOrderWithdrawMapper slavePayOrderWithdrawMapper;
//    @Resource
//    private SlaveBetImOrderMapper slaveBetImOrderMapper;

    /**
     * 资金报表 因为统一订单，充值、提现、游戏状态可能每天不一样，所以暂时只能实时查询，后期优化
     *
     * @param req
     * @return
     */
    public FundsReportVO queryFundsReport(FundsReportReq req) {

        FundsReportVO vo = new FundsReportVO();
        List<FundsReportListVO> list = new ArrayList<>();
        FundsReportSummaryVO summary = new FundsReportSummaryVO();

        if (StringUtils.isBlank(req.getBeginDate())) {
            throw new BusinessException("起始时间为空");
        }
        if (StringUtils.isBlank(req.getEndDate())) {
            throw new BusinessException("结束时间为空");
        }
        Date startDate = DateUtil.beginOfDay(DateUtil.parse(req.getBeginDate()));
        Date endDate = DateUtil.endOfDay(DateUtil.parse(req.getEndDate()));
        if (startDate.compareTo(endDate) > 0) {
            throw new BusinessException("结束日期不应早于开始日期");
        }
        long daynums = DateInnerUtil.betweenDay(startDate, endDate);
        if (daynums > EXP_FUND_STATISTICS_DAYS) {
            throw new BusinessException("时间不能超过60天");
        }

        //某个时间段内所有的日期
        List<DateTime> dateTimes = DateUtil.rangeToList(startDate, endDate, DateField.DAY_OF_YEAR);

        List<FundsRechargeDTO> rechargeDTOList = slavePayOrderRechargeMapper.queryFundsRecharge(dateTimes);
        List<FundsWithdrawDTO> withdrawDTOList = slavePayOrderWithdrawMapper.queryFundsWithdraw(dateTimes);
        List<FundsImOrderDTO> imOrderDTOList = new ArrayList<>();

        for (int i = 0; i < dateTimes.size(); i++) {
            FundsRechargeDTO rechargeDTO = rechargeDTOList.get(i);
            FundsWithdrawDTO withdrawDTO = withdrawDTOList.get(i);
//            FundsImOrderDTO imOrderDTO = imOrderDTOList.get(i);

            FundsReportListVO po = new FundsReportListVO();
            //查询日期
            po.setReportDate(rechargeDTO.getReportDate());
            //充值总额
            po.setTotalRecharge(rechargeDTO.getSumRecharge());
            //首充人数
            po.setFirstRechargeNum(rechargeDTO.getSumFirstNum());
            //提现总额
            po.setTotalWithdraw(withdrawDTO.getSumWithdraw());
//            // 总下注金额
//            po.setGameTotalComsumer(imOrderDTO.getTotalComsumer());
//            // 中奖金额
//            po.setPlayWinAmount(imOrderDTO.getPlayWinAmount());
            //游戏总盈利： 总下注金额-中奖金额
//            po.setTotalGameProfit(po.getGameTotalComsumer().subtract(po.getPlayWinAmount()));
            //总盈利 TODO  暂时是IM体育总盈利
            po.setTotalProfit(po.getTotalGameProfit());

            list.add(po);

            //实时计算汇总数据
            summary.setTotalRecharge(summary.getTotalRecharge().add(po.getTotalRecharge()));
            summary.setTotalWithdraw(summary.getTotalWithdraw().add(po.getTotalWithdraw()));
            summary.setTotalGameProfit(summary.getTotalGameProfit().add(po.getTotalGameProfit()));
            summary.setTotalProfit(summary.getTotalProfit().add(po.getTotalProfit()));

        }


//        for (DateTime dateTime : dateTimes) {
//            //实时查询统计数据
//            FundsReportListVO po = everyDayReport(dateTime);
//            list.add(po);
//            //实时计算汇总数据
//            summary.setTotalRecharge(summary.getTotalRecharge().add(po.getTotalRecharge()));
//            summary.setTotalWithdraw(summary.getTotalWithdraw().add(po.getTotalWithdraw()));
//            summary.setTotalGameProfit(summary.getTotalGameProfit().add(po.getTotalGameProfit()));
//            summary.setTotalProfit(summary.getTotalProfit().add(po.getTotalProfit()));
//        }

        vo.setList(list);
        vo.setSummary(summary);
        return vo;
    }

    /**
     * 生成指定日期的资金报表
     *
     * @param day
     */
    private FundsReportListVO everyDayReport(Date day) {
        FundsReportListVO po = new FundsReportListVO();
        po.setReportDate(day);
        Date end = DateUtil.offsetDay(po.getReportDate(), 1);
        po.setTotalRecharge(slavePayOrderRechargeMapper.sumRecharge(po.getReportDate(), end));
        po.setFirstRechargeNum(slavePayOrderRechargeMapper.sumFirstNum(po.getReportDate(), end));

        po.setTotalWithdraw(slavePayOrderWithdrawMapper.sumWithdraw(po.getReportDate(), end));

//        BigDecimal totalComsumer = betImOrderService.getTotalComsumer(po.getReportDate(), end);
//        BigDecimal playWinAmount = betImOrderService.getPlayWinAmount(po.getReportDate(), end);
//        // 总下注金额
//        po.setGameTotalComsumer(totalComsumer);
//        // 中奖金额
//        po.setPlayWinAmount(playWinAmount);
        //游戏总盈利： 总下注金额-中奖金额
        po.setTotalGameProfit(po.getGameTotalComsumer().subtract(po.getPlayWinAmount()));
        //总盈利 TODO  暂时是IM体育总盈利
        po.setTotalProfit(po.getTotalGameProfit());

        return po;
    }

}
