package com.onelive.manage.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.req.report.UserReportDetailReq;
import com.onelive.common.model.vo.report.MemGoldChangeBackVO;
import com.onelive.common.model.vo.report.UserReportDetailAllVO;
import com.onelive.common.mybatis.entity.MemGoldchange;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员金额变动明细 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-12
 */
public interface MemGoldchangeService extends IService<MemGoldchange> {

    UserReportDetailAllVO queryUserDetail(UserReportDetailReq req);

    List<MemGoldChangeBackVO> listPage(Date startTime, Date endTime, String nickName, String account, Integer changeType);
}
