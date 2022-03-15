package com.onelive.manage.modules.report.business;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.report.UserReportDetailReq;
import com.onelive.common.model.req.report.UserReportReq;
import com.onelive.common.model.vo.report.UserReportDetailAllVO;
import com.onelive.common.model.vo.report.UserReportVO;
import com.onelive.manage.service.mem.MemGoldchangeService;
import com.onelive.manage.service.mem.MemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName UserReportBusiness
 * @Desc 会员报表业务类
 * @Date 2021/4/13 15:08
 */
@Component
@Slf4j
public class UserReportBusiness {

    @Resource
    private MemUserService memUserService;
    @Resource
    private MemGoldchangeService memGoldchangeService;

    /**
     * 查询用户报表
     *
     * @param req
     * @return
     */
    public PageInfo<UserReportVO> queryUserReport(UserReportReq req) {
        return memUserService.queryUserReport(req);
    }

    /**
     * 查询用户报表明细
     *
     * @param req
     * @return
     */
    public UserReportDetailAllVO queryUserDetail(UserReportDetailReq req) {
        return memGoldchangeService.queryUserDetail(req);
    }
}
