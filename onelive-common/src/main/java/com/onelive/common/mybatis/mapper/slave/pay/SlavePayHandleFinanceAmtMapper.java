package com.onelive.common.mybatis.mapper.slave.pay;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.report.HandleDTO;
import com.onelive.common.model.vo.pay.PayHandleFinanceAmtBackVO;
import com.onelive.common.model.vo.report.ArtificialAmtVO;
import com.onelive.common.mybatis.entity.PayHandleFinanceAmt;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 人工加减款记录表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-04-19
 */
public interface SlavePayHandleFinanceAmtMapper extends BaseMapper<PayHandleFinanceAmt> {

    List<PayHandleFinanceAmtBackVO> listPage(String nickname, String account,String accno);

    HandleDTO queryHandleSummary(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<ArtificialAmtVO> getArtificialAmt(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
