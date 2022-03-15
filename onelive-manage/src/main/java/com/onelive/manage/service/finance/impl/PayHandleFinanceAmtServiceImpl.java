package com.onelive.manage.service.finance.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.dto.report.HandleDTO;
import com.onelive.common.model.vo.pay.PayHandleFinanceAmtBackVO;
import com.onelive.common.model.vo.report.ArtificialAmtVO;
import com.onelive.common.mybatis.entity.PayHandleFinanceAmt;
import com.onelive.common.mybatis.mapper.master.pay.PayHandleFinanceAmtMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayHandleFinanceAmtMapper;
import com.onelive.manage.service.finance.PayHandleFinanceAmtService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 人工加减款记录表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-19
 */
@Service
public class PayHandleFinanceAmtServiceImpl extends ServiceImpl<PayHandleFinanceAmtMapper, PayHandleFinanceAmt> implements PayHandleFinanceAmtService {

    @Resource
    private SlavePayHandleFinanceAmtMapper slavePayHandleFinanceAmtMapper;


    @Override
    public SlavePayHandleFinanceAmtMapper getSalveMapper() {
        return this.slavePayHandleFinanceAmtMapper;
    }

    @Override
    public List<PayHandleFinanceAmtBackVO> listPage(String nickname, String account,String accno) {
        return slavePayHandleFinanceAmtMapper.listPage(nickname, account,accno);
    }

    @Override
    public HandleDTO queryHandleSummary(Date startDate, Date endDate) {
        return slavePayHandleFinanceAmtMapper.queryHandleSummary(startDate, endDate);
    }

    @Override
    public List<ArtificialAmtVO> getArtificialAmt(Date startTime, Date endTime) {
        return slavePayHandleFinanceAmtMapper.getArtificialAmt(startTime, endTime);
    }
}
