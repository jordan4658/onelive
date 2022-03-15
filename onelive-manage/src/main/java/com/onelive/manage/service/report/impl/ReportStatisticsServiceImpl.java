package com.onelive.manage.service.report.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.report.StatSourceDTO;
import com.onelive.common.model.req.report.GameDetailReportReq;
import com.onelive.common.model.req.report.GameReportReq;
import com.onelive.common.model.req.report.GameSelectReq;
import com.onelive.common.model.vo.report.GameDetailReportVO;
import com.onelive.common.model.vo.report.GameReportVO;
import com.onelive.common.model.vo.report.GameSelectVO;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayOrderRechargeMapper;
import com.onelive.common.mybatis.mapper.slave.pay.SlavePayOrderWithdrawMapper;
import com.onelive.common.mybatis.mapper.slave.report.GameReportMapper;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.manage.service.report.ReportStatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * @author lorenzo
 * @Description 报表统计服务实现类
 * @Date 2021/5/25 10:02
 */
@Service
public class ReportStatisticsServiceImpl implements ReportStatisticsService {

    @Resource
    private SlavePayOrderRechargeMapper slavePayOrderRechargeMapper;

    @Resource
    private SlavePayOrderWithdrawMapper slavePayOrderWithdrawMapper;

//    @Resource
//    private SlaveBetImOrderMapper slaveBetImOrderMapper;

    @Resource
    private SlaveMemUserMapper slaveMemUserMapper;
    @Resource
    private GameReportMapper gameReportMapper;

    @Override
    public List<StatSourceDTO> totalRecharge(Date startTime, Date endTime) {
        return slavePayOrderRechargeMapper.totalRecharge(startTime, endTime);
    }

    @Override
    public BigDecimal firstRecharge(Date startTime, Date endTime) {
        return slavePayOrderRechargeMapper.firstRecharge(startTime, endTime);
    }

    @Override
    public int countRechargeUser(Date startTime, Date endTime) {
        return slavePayOrderRechargeMapper.countRechargeUser(startTime, endTime);
    }

    @Override
    public List<StatSourceDTO> totalWithdraw(Date startTime, Date endTime) {
        return slavePayOrderWithdrawMapper.totalWithdraw(startTime, endTime);
    }

//    @Override
//    public List<StatSourceBetDTO> queryBetReport(Date startTime, Date endTime) {
//        return slaveBetImOrderMapper.queryBetReport(WalletAction.getValidStatus(), startTime, endTime);
//    }

    @Override
    public int countRegisteredUser(Date startTime, Date endTime) {
        LambdaQueryWrapper<MemUser> wrapper = Wrappers.<MemUser>lambdaQuery()
                .between(startTime !=null && endTime !=null, MemUser::getRegisterTime, startTime, endTime);
        return slaveMemUserMapper.selectCount(wrapper);
    }

    @Override
    public PageInfo<GameReportVO> queryGameReport(GameReportReq req) {
        PageInfo<GameReportVO> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> gameReportMapper.queryGameReport(req));
        if(pageInfo!=null){
            List<GameReportVO> list = pageInfo.getList();
            if(CollectionUtil.isNotEmpty(list)){
                list.stream().forEach(vo->{
                    BigDecimal betAmount = vo.getBetAmount();
                    BigDecimal winAmount = vo.getWinAmount();
                    if(betAmount==null){
                        betAmount = BigDecimal.ZERO;
                    }
                    if(winAmount==null){
                        winAmount = BigDecimal.ZERO;
                    }
                    //计算用户盈亏
                    BigDecimal userWinAmount = winAmount.subtract(betAmount);
                    //计算平台盈亏
                    BigDecimal platformWinAmount = betAmount.subtract(winAmount);
                    //计算盈率
                    if(betAmount.compareTo(BigDecimal.ZERO)>0) {
                        BigDecimal ratio = platformWinAmount.divide(betAmount, 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
                        vo.setRatio(ratio);
                    }else{
                        vo.setRatio(BigDecimal.ZERO);
                    }
                    vo.setUserWinAmount(userWinAmount);
                    vo.setPlatformWinAmount(platformWinAmount);
                });
            }
        }
        return pageInfo;
    }

    @Override
    public PageInfo<GameDetailReportVO> queryGameDetailReport(GameDetailReportReq req) {
        PageInfo<GameDetailReportVO> pageInfo = PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> gameReportMapper.queryGameDetailReport(req));
        if(pageInfo!=null){
            List<GameDetailReportVO> list = pageInfo.getList();
            if(CollectionUtil.isNotEmpty(list)){
                list.stream().forEach(vo->{
                    BigDecimal betAmount = vo.getBetAmount();
                    BigDecimal winAmount = vo.getWinAmount();
                    if(betAmount==null){
                        betAmount = BigDecimal.ZERO;
                    }
                    if(winAmount==null){
                        winAmount = BigDecimal.ZERO;
                    }
                    //计算用户盈亏
                    BigDecimal userWinAmount = winAmount.subtract(betAmount);
                    //计算平台盈亏
                    BigDecimal platformWinAmount = betAmount.subtract(winAmount);
                    //计算盈率
                    if(betAmount.compareTo(BigDecimal.ZERO)>0) {
                        BigDecimal ratio = platformWinAmount.divide(betAmount, 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
                        vo.setRatio(ratio);
                    }else{
                        vo.setRatio(BigDecimal.ZERO);
                    }
                    vo.setUserWinAmount(userWinAmount);
                    vo.setPlatformWinAmount(platformWinAmount);
                });
            }
        }
        return pageInfo;
    }

    /**
     * 查询下拉列表
     * @param req
     * @return
     */
    @Override
    public List<GameSelectVO> queryGameSelect(GameSelectReq req) {
        return gameReportMapper.queryGameSelect(req);
    }
}
