package com.onelive.manage.modules.report.business;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.other.SimpleConstant;
import com.onelive.common.constants.other.SymbolConstant;
import com.onelive.common.enums.DepositEnums;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.report.HandleDTO;
import com.onelive.common.model.dto.report.OnlineCompanyDTO;
import com.onelive.common.model.dto.report.OnlineReportDTO;
import com.onelive.common.model.req.report.DepositOnlineReq;
import com.onelive.common.model.req.report.DepositReq;
import com.onelive.common.model.vo.report.DepositOnlineVO;
import com.onelive.common.model.vo.report.DepositVO;
import com.onelive.manage.service.finance.PayHandleFinanceAmtService;
import com.onelive.manage.service.finance.PayOrderRechargeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DepositReportBusiness
 * @Desc 入款报表业务类
 */
@Component
@Slf4j
public class DepositReportBusiness {

    @Resource
    private PayHandleFinanceAmtService payHandleFinanceAmtService;
    @Resource
    private PayOrderRechargeService payOrderRechargeService;

    /**
     * 资金报表查询
     *
     * @param req
     * @return
     */
    public List<DepositVO> queryList(DepositReq req) {
        Date startDate;
        Date endDate;
        Date nowDate = new Date();
        if (StringUtils.isBlank(req.getBeginDate())) {
            startDate = DateUtil.beginOfDay(nowDate);
        } else {
            startDate = DateUtil.beginOfDay(DateUtil.parse(req.getBeginDate()));
        }
        if (StringUtils.isBlank(req.getEndDate())) {
            endDate = DateUtil.endOfDay(nowDate);
        } else {
            endDate = DateUtil.endOfDay(DateUtil.parse(req.getEndDate()));
        }
        if (endDate.getTime() < startDate.getTime()) {
            throw new BusinessException("开始时间不能大于结束时间");
        }

        List<DepositVO> resultList = new ArrayList<>();
        //总充值人数
        Integer totalPeopleNum = 0;
        //总成功次数
        Integer totalSuccessNum = 0;
        //总失败次数
        Integer totalFailNum = 0;
        //总入款金额
        BigDecimal totalDepositAmount = BigDecimal.ZERO;

        //人工入款
        HandleDTO handleDTO = payHandleFinanceAmtService.queryHandleSummary(startDate, endDate);
        totalPeopleNum = handleDTO.getTotalPeople();
        totalDepositAmount = handleDTO.getTotalMoney();

        DepositVO handleVO = new DepositVO();
        handleVO.setDepositType(DepositEnums.HANDLE.getMsg());
        handleVO.setPeopleNum(handleDTO.getTotalPeople().toString());
        handleVO.setSuccessNum(SymbolConstant.STRIKETHROUGH);
        handleVO.setFailNum(SymbolConstant.STRIKETHROUGH);
        handleVO.setSuccessRate(SymbolConstant.STRIKETHROUGH);
        handleVO.setDepositAmount(handleDTO.getTotalMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());

        //公司入款
        OnlineCompanyDTO companyDTO = payOrderRechargeService.queryOnlineCompanySummery(startDate, endDate, PayConstants.PayProviderTypeEnum.OFFLINE.getCode());
        totalPeopleNum = totalPeopleNum + companyDTO.getSuccessPeople();
        totalSuccessNum = totalSuccessNum + companyDTO.getSuccessCount();
        totalFailNum = totalFailNum + companyDTO.getFailCount();
        totalDepositAmount = totalDepositAmount.add(companyDTO.getSuccessMoney());

        DepositVO companyVO = new DepositVO();
        companyVO.setDepositType(DepositEnums.COMPANY.getMsg());
        //已付款的总人数
        companyVO.setPeopleNum(companyDTO.getSuccessPeople().toString());
        //成功次数    已付款的次数
        companyVO.setSuccessNum(companyDTO.getSuccessCount().toString());
        //失败次数     充值失败 和  用户取消
        companyVO.setFailNum(companyDTO.getFailCount().toString());
        //成功率      成功付款次数/  成功付款次数+失败次数
        companyVO.setSuccessRate(getPer(companyDTO.getSuccessCount(), (companyDTO.getSuccessCount() + companyDTO.getFailCount())));
        //已付款的总金额  成功付款金额
        companyVO.setDepositAmount(companyDTO.getSuccessMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());

        //线上入款
        OnlineCompanyDTO onlineDTO = payOrderRechargeService.queryOnlineCompanySummery(startDate, endDate, PayConstants.PayProviderTypeEnum.ONLINE.getCode());
        totalPeopleNum = totalPeopleNum + onlineDTO.getSuccessPeople();
        totalSuccessNum = totalSuccessNum + onlineDTO.getSuccessCount();
        totalFailNum = totalFailNum + onlineDTO.getFailCount();
        totalDepositAmount = totalDepositAmount.add(onlineDTO.getSuccessMoney());

        DepositVO onlineVO = new DepositVO();
        onlineVO.setDepositType(DepositEnums.ONLINE.getMsg());
        onlineVO.setPeopleNum(onlineDTO.getSuccessPeople().toString());
        onlineVO.setSuccessNum(onlineDTO.getSuccessCount().toString());
        onlineVO.setFailNum(onlineDTO.getFailCount().toString());
        onlineVO.setSuccessRate(getPer(onlineDTO.getSuccessPeople(), (onlineDTO.getSuccessCount() + onlineDTO.getFailCount())));
        onlineVO.setDepositAmount(onlineDTO.getSuccessMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());

        //代充充值 暂无该业务，先写死
        DepositVO proxyVO = new DepositVO();
        proxyVO.setDepositType(DepositEnums.proxy.getMsg());
        proxyVO.setPeopleNum(SimpleConstant.ZERO);
        proxyVO.setSuccessNum(SymbolConstant.STRIKETHROUGH);
        proxyVO.setFailNum(SymbolConstant.STRIKETHROUGH);
        proxyVO.setSuccessRate(SymbolConstant.STRIKETHROUGH);
        proxyVO.setDepositAmount(SimpleConstant.ZERO);

        //小计
        DepositVO totalVO = new DepositVO();
        totalVO.setDepositType(DepositEnums.total.getMsg());
        totalVO.setPeopleNum(totalPeopleNum.toString());
        totalVO.setSuccessNum(totalSuccessNum.toString());
        totalVO.setFailNum(totalFailNum.toString());
        totalVO.setSuccessRate(SymbolConstant.STRIKETHROUGH);
        totalVO.setDepositAmount(totalDepositAmount.setScale(2, BigDecimal.ROUND_DOWN).toString());

        resultList.add(handleVO);
        resultList.add(companyVO);
        resultList.add(onlineVO);
        resultList.add(proxyVO);
        resultList.add(totalVO);
        return resultList;
    }

    /**
     * 线上支付方式报表查询
     *
     * @param req
     * @return
     */
    public List<DepositOnlineVO> queryOnlineList(DepositOnlineReq req) {

        Date startDate;
        Date endDate;
        Date nowDate = new Date();
        if (StringUtils.isBlank(req.getBeginDate())) {
            startDate = DateUtil.beginOfDay(nowDate);
        } else {
            startDate = DateUtil.beginOfDay(DateUtil.parse(req.getBeginDate()));
        }
        if (StringUtils.isBlank(req.getEndDate())) {
            endDate = DateUtil.endOfDay(nowDate);
        } else {
            endDate = DateUtil.endOfDay(DateUtil.parse(req.getEndDate()));
        }
        if (endDate.getTime() < startDate.getTime()) {
            throw new BusinessException("开始时间不能大于结束时间");
        }

        req.setBeginDate(DateUtil.formatDate(startDate));
        req.setBeginDate(DateUtil.formatDate(endDate));

        List<DepositOnlineVO> voList = new ArrayList<>();
        List<OnlineReportDTO> list = payOrderRechargeService.queryOnlineReportDTOSummery(req);
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().map(a -> {
                DepositOnlineVO vo = new DepositOnlineVO();
                //支付设定名称
                vo.setPayTypeName(a.getPayTypeName());
                //支付商名称
                vo.setProviderName(a.getProviderName());
                //支付方式
                vo.setPayWayName(a.getPayWayName());
                //已付款的总人数
                vo.setPeopleNum(a.getSuccessPeople().toString());
                //成功次数    已付款的次数
                vo.setSuccessNum(a.getSuccessCount().toString());
                //失败次数     充值失败 和  用户取消
                vo.setFailNum(a.getFailCount().toString());
                //成功率      成功付款次数/  成功付款次数+失败次数
                vo.setSuccessRate(getPer(a.getSuccessPeople(), (a.getSuccessCount() + a.getFailCount())));
                //已付款的总金额  成功付款金额
                vo.setDepositAmount(a.getSuccessMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
                return vo;
            }).collect(Collectors.toList());
        }
        return voList;
    }


    //字符串 百分比
    public static String getPer(Integer a, Integer b) {
        if (b == 0) {
            return "0.00%";
        }
        BigDecimal result = BigDecimal.valueOf(a).divide(BigDecimal.valueOf(b), 4, BigDecimal.ROUND_HALF_UP);
        return NumberUtil.decimalFormat("#.##%", result.doubleValue());
    }

}
