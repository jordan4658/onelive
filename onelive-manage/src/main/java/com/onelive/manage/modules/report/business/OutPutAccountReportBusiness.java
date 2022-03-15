package com.onelive.manage.modules.report.business;

import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.vo.report.ArtificialAmtVO;
import com.onelive.common.model.vo.report.OnlineAmtAndOfflineAmtVO;
import com.onelive.common.model.vo.report.OutPutAccountVO;
import com.onelive.common.utils.others.DateInnerUtil;
import com.onelive.manage.service.finance.PayHandleFinanceAmtService;
import com.onelive.manage.service.finance.PayOrderRechargeService;
import com.onelive.manage.service.finance.PayOrderWithdrawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: OutPutAccountReportBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/5/4 11:25
 */
@Component
@Slf4j
public class OutPutAccountReportBusiness {

    @Resource
    private PayOrderRechargeService payOrderRechargeService;
    @Resource
    private PayOrderWithdrawService payOrderWithdrawService;
    @Resource
    private PayHandleFinanceAmtService payHandleFinanceAmtService;


    public OutPutAccountVO getInfo(Date startTime, Date endTime) {
        if (startTime == null) {
            startTime = DateInnerUtil.beginOfDay(new Date());
        } else {
            startTime = DateInnerUtil.beginOfDay(startTime);
        }
        if (endTime == null) {
            endTime = DateInnerUtil.endOfDay(new Date());
        } else {
            endTime = DateInnerUtil.endOfDay(endTime);
        }
        if (endTime.compareTo(startTime) < 0) {
            throw new BusinessException("开始日期大于结束日期！");
        }
        OutPutAccountVO outPutAccountVO = new OutPutAccountVO();
        //查询线下/线上入款金额
        List<OnlineAmtAndOfflineAmtVO> rechargeList = payOrderRechargeService.getOnlineAmtAndOfflineAmt(startTime, endTime);
        rechargeList.stream().forEach(recharge ->{
            if(recharge.getOrderType()== PayConstants.PayProviderTypeEnum.ONLINE.getCode()){
                outPutAccountVO.setOnlineAmt(recharge.getAmt()); //线上入款赋值
            }else if(recharge.getOrderType()==PayConstants.PayProviderTypeEnum.OFFLINE.getCode()){
                outPutAccountVO.setOfflineAmt(recharge.getAmt()); //线下入款赋值
            }
        });
        //TODO 查询活动金额 暂时还没有设计这块

        //TODO 查询返水金额 暂时还没有设计这块
        //查询提现金额
        BigDecimal withdrawAmt = payOrderWithdrawService.getWithdrawAmt(startTime, endTime);
        outPutAccountVO.setWithdrawAmt(withdrawAmt);
        //查询手动加减款金额  List.get(0)-减款、List.get(2)-加款
        List<ArtificialAmtVO> artificialList=payHandleFinanceAmtService.getArtificialAmt(startTime, endTime);
        artificialList.stream().forEach(amt-> {
            //手动减款
            if(amt.getHandleType()== PayConstants.AccountChangTypeEnum.CHANG_TYPE10.getPayTypeCode()){
                outPutAccountVO.setArtificialSubAmt(amt.getArtificialAmt());
            }else if(amt.getHandleType()== PayConstants.AccountChangTypeEnum.CHANG_TYPE11.getPayTypeCode()){
                outPutAccountVO.setArtificialAddAmt(amt.getArtificialAmt());
            }
        });
        return outPutAccountVO;
    }
}
