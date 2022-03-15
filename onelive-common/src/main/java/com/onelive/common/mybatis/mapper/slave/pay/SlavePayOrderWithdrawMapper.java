package com.onelive.common.mybatis.mapper.slave.pay;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.report.FundsWithdrawDTO;
import com.onelive.common.model.dto.report.StatSourceDTO;
import com.onelive.common.model.req.risk.RiskAuditWithdrawReq;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.model.vo.pay.PayOrderWithdrawBackVO;
import com.onelive.common.model.vo.pay.PayWithdrawResultsVO;
import com.onelive.common.model.vo.risk.RiskAuditWithdrawVO;
import com.onelive.common.mybatis.entity.PayOrderWithdraw;
import com.onelive.common.mybatis.sqlProvider.ReportSqlProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 提现申请 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-04-14
 */
public interface SlavePayOrderWithdrawMapper extends BaseMapper<PayOrderWithdraw> {

    List<MemGoldchangeVO> withdrawRecordList(@Param("startDate") Date startDate,
                                             @Param("endDate") Date endDate,
                                             @Param("account") String account);

    List<PayOrderWithdrawBackVO> listPage(@Param("account") String account,
                                          @Param("orderStatus") Integer orderStatus,
                                          @Param("orderNo") String orderNo,
                                          @Param("startDate") Date startDate,
                                          @Param("endDate") Date endDate);

    BigDecimal sumWithdraw(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    /**
     * 资金报表提现信息查询
     *
     * @param dateList
     * @return
     */
    @SelectProvider(type = ReportSqlProvider.class, method = "queryFundsWithdraw")
    List<FundsWithdrawDTO> queryFundsWithdraw(@Param("dateList") List<DateTime> dateList);

    PayWithdrawResultsVO getWithdrawDetailsByOrderNo(@Param("orderNo")String orderNo, @Param("account") String account);

    /**
     * 根据时间查询平台提现金额
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal getWithdrawAmt(Date startTime, Date endTime);

    List<StatSourceDTO> totalWithdraw(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 查询提现列表分页
     * @return
     */
    List<RiskAuditWithdrawVO> withdrawListPage(RiskAuditWithdrawReq riskAuditWithdrawReq);
}
