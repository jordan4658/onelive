package com.onelive.manage.service.report;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.report.StatSourceDTO;
import com.onelive.common.model.req.report.GameDetailReportReq;
import com.onelive.common.model.req.report.GameReportReq;
import com.onelive.common.model.req.report.GameSelectReq;
import com.onelive.common.model.vo.report.GameDetailReportVO;
import com.onelive.common.model.vo.report.GameReportVO;
import com.onelive.common.model.vo.report.GameSelectVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 报表统计服务类
 */
public interface ReportStatisticsService {

    /**
     * 统计充值
     * @param startTime
     * @param endTime
     * @return
     */
    List<StatSourceDTO> totalRecharge(Date startTime, Date endTime);

    /**
     * 统计提现
     * @param startTime
     * @param endTime
     * @return
     */
    List<StatSourceDTO> totalWithdraw(Date startTime, Date endTime);

    /**
     * 首次充值
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal firstRecharge(Date startTime, Date endTime);

    /**
     * 充值人数
     * @param startTime
     * @param endTime
     * @return
     */
    int countRechargeUser(Date startTime, Date endTime);

//    /**
//     * 查询注单报表
//     * @param startTime
//     * @param endTime
//     * @return
//     */
//    List<StatSourceBetDTO> queryBetReport(Date startTime, Date endTime);

    /**
     * 统计注册人数
     * @param startTime
     * @param endTime
     * @return
     */
    int countRegisteredUser(Date startTime, Date endTime);

    /**
     * 统计游戏报表数据
     * @param req
     * @return
     */
    PageInfo<GameReportVO> queryGameReport(GameReportReq req);

    /**
     * 查询指定平台的游戏详情报表数据
     * @param req
     * @return
     */
    PageInfo<GameDetailReportVO> queryGameDetailReport(GameDetailReportReq req);

    /**
     * 查询游戏下拉选项
     * @param req
     * @return
     */
    List<GameSelectVO> queryGameSelect(GameSelectReq req);
}
