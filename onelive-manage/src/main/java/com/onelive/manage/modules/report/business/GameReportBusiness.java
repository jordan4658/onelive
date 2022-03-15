package com.onelive.manage.modules.report.business;

import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.GameReportColumnEnums;
import com.onelive.common.model.req.report.GameDetailReportReq;
import com.onelive.common.model.req.report.GameReportReq;
import com.onelive.common.model.req.report.GameSelectReq;
import com.onelive.common.model.vo.report.ColumnSelectVO;
import com.onelive.common.model.vo.report.GameDetailReportVO;
import com.onelive.common.model.vo.report.GameReportVO;
import com.onelive.common.model.vo.report.GameSelectVO;
import com.onelive.manage.service.report.ReportStatisticsService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 游戏报表业务类
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameReportBusiness {


    @Resource
    private ReportStatisticsService statisticsService;

    /**
     * 查询游戏报表数据
     * @param req
     * @return
     */
    public PageInfo<GameReportVO> queryGameReport(GameReportReq req) {
        return statisticsService.queryGameReport(req);
    }

    /**
     * 查询指定平台的游戏统计数据
     * @param req
     * @return
     */
    public PageInfo<GameDetailReportVO> queryGameDetailReport(GameDetailReportReq req) {
        return statisticsService.queryGameDetailReport(req);
    }

    /**
     * 查询下拉游戏选项
     * @param req
     * @return
     */
    public List<GameSelectVO> queryGameSelect(GameSelectReq req) {
        return statisticsService.queryGameSelect(req);
    }

    /**
     * 下拉选项
     * @return
     */
    public List<ColumnSelectVO> queryColumnSelect() {
        List<ColumnSelectVO> list = new LinkedList<>();
        GameReportColumnEnums[] values = GameReportColumnEnums.values();
        for (GameReportColumnEnums en : values) {
            ColumnSelectVO vo = new ColumnSelectVO(en.getName(),en.getColumn());
            list.add(vo);
        }
        return list;
    }
}
