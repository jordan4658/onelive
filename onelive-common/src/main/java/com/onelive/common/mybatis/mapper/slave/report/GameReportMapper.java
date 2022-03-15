package com.onelive.common.mybatis.mapper.slave.report;

import com.onelive.common.model.req.report.GameDetailReportReq;
import com.onelive.common.model.req.report.GameReportReq;
import com.onelive.common.model.req.report.GameSelectReq;
import com.onelive.common.model.vo.report.GameDetailReportVO;
import com.onelive.common.model.vo.report.GameReportVO;
import com.onelive.common.model.vo.report.GameSelectVO;
import com.onelive.common.mybatis.sqlProvider.GameReportSqlProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 游戏报表
 */
public interface GameReportMapper {

    @SelectProvider(value = GameReportSqlProvider.class, method = "queryGameReport")
    List<GameReportVO> queryGameReport(GameReportReq req);

    @SelectProvider(value = GameReportSqlProvider.class, method = "queryGameDetailReport")
    List<GameDetailReportVO> queryGameDetailReport(GameDetailReportReq req);

    @SelectProvider(value = GameReportSqlProvider.class, method = "queryGameSelect")
    List<GameSelectVO> queryGameSelect(GameSelectReq req);
}
