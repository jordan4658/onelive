package com.onelive.manage.service.finance;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.report.HandleDTO;
import com.onelive.common.model.vo.pay.PayHandleFinanceAmtBackVO;
import com.onelive.common.model.vo.report.ArtificialAmtVO;
import com.onelive.common.mybatis.entity.PayHandleFinanceAmt;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayHandleFinanceAmtMapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 人工加减款记录表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-19
 */
public interface PayHandleFinanceAmtService extends IService<PayHandleFinanceAmt> {

    /**
     * 获取从的mapper
     *
     * @return
     */
    SlavePayHandleFinanceAmtMapper getSalveMapper();

    List<PayHandleFinanceAmtBackVO> listPage(String nickname, String account,String accno);

    /**
     * 手动入账查询
     *
     * @param startDate
     * @param endDate
     * @return
     */
    HandleDTO queryHandleSummary(Date startDate, Date endDate);

    /**
     * 根据时间获取 平台人工加减款金额
     * @param startTime
     * @param endTime
     * @return
     */
    List<ArtificialAmtVO> getArtificialAmt(Date startTime, Date endTime);
}
