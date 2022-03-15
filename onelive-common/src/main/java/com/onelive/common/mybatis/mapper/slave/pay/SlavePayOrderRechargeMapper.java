package com.onelive.common.mybatis.mapper.slave.pay;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.report.FundsRechargeDTO;
import com.onelive.common.model.dto.report.OnlineCompanyDTO;
import com.onelive.common.model.dto.report.OnlineReportDTO;
import com.onelive.common.model.dto.report.StatSourceDTO;
import com.onelive.common.model.req.report.DepositOnlineReq;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.model.vo.pay.OfflinePayOrderRechargeBackVO;
import com.onelive.common.model.vo.pay.OnlinePayOrderRechargeBackVO;
import com.onelive.common.model.vo.report.OnlineAmtAndOfflineAmtVO;
import com.onelive.common.mybatis.entity.PayOrderRecharge;
import com.onelive.common.mybatis.sqlProvider.ReportSqlProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单信息主 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-04-08
 */
public interface SlavePayOrderRechargeMapper extends BaseMapper<PayOrderRecharge> {

    List<MemGoldchangeVO> rechargeRecordList(@Param("startDate") Date startDate,
                                             @Param("endDate") Date endDate,
                                             @Param("account") String account);

    List<OfflinePayOrderRechargeBackVO> offlineListPage(@Param("orderType") Integer orderType,
                                                        @Param("orderStatus") Integer orderStatus,
                                                        @Param("account") String account,
                                                        @Param("orderNo") String orderNo,
                                                        @Param("startDate") Date startDate,
                                                        @Param("endDate") Date endDate);

    List<OnlinePayOrderRechargeBackVO> onlineListPage(@Param("orderType") Integer orderType,
                                                      @Param("orderStatus") Integer orderStatus,
                                                      @Param("account") String account,
                                                      @Param("orderNo") String orderNo,
                                                      @Param("providerId") String providerId,
                                                      @Param("startDate") Date startDate,
                                                      @Param("endDate") Date endDate);

    BigDecimal sumRecharge(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    Integer sumFirstNum(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    OnlineCompanyDTO queryOnlineCompanySummery(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("orderType") Integer orderType);

    MemGoldchangeVO getByAccountAndOrderNo(@Param("account") String account, @Param("orderNo") String orderNo);

    List<OnlineReportDTO> queryOnlineReportDTOSummery(DepositOnlineReq req);

    /**
     * 资金报表充值信息查询
     *
     * @param dateList
     * @return
     */
    @SelectProvider(type = ReportSqlProvider.class, method = "queryFundsRecharge")
    List<FundsRechargeDTO> queryFundsRecharge(@Param("dateList") List<DateTime> dateList);

    List<OnlineAmtAndOfflineAmtVO> getOnlineAmtAndOfflineAmt(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<StatSourceDTO> totalRecharge(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    BigDecimal firstRecharge(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int countRechargeUser(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
