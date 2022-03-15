package com.onelive.manage.service.agent;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.report.AgentReportListReq;
import com.onelive.common.model.vo.report.AgentReportListVo;
import com.onelive.common.mybatis.entity.AgentInviteRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-16
 */
public interface AgentInviteRecordService extends IService<AgentInviteRecord> {

    PageInfo<AgentReportListVo> getReportList(AgentReportListReq param);
}
