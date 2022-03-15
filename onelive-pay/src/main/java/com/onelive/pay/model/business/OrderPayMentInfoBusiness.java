package com.onelive.pay.model.business;

import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.req.pay.OrderDetailsByOrderNoReq;
import com.onelive.common.model.vo.mem.MemGoldchangeVO;
import com.onelive.common.mybatis.entity.PayMentInfo;
import com.onelive.common.mybatis.entity.PayOrderRecharge;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.pay.service.PayMentInfoService;
import com.onelive.pay.service.PayOrderRechargeService;
import com.onelive.pay.service.SysBusParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: OrderPayMentInfoBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/12 14:43
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderPayMentInfoBusiness {

    @Resource
    private PayOrderRechargeService payOrderRechargeService;
    @Resource
    private PayMentInfoService payMentInfoService;
    @Resource
    private SysBusParameterService sysBusParameterService;

    @Transactional
    public Boolean insertOrderAndPayMentInfo(PayOrderRecharge orderRecharge, PayMentInfo mentInfo) {
        Boolean orderFlag = payOrderRechargeService.save(orderRecharge);
        if (!orderFlag) {
            log.error("充值订单新增失败！");
            return false;
        }
        Boolean mentInfoFlag = payMentInfoService.save(mentInfo);
        if (!mentInfoFlag) {
            log.error("支付流水订单处理失败！");
            return false;
        }
        return true;
    }


    public MemGoldchangeVO getOrderDetailsByOrderNo(AppLoginUser user, OrderDetailsByOrderNoReq req) {
        if(StringUtils.isEmpty(req.getOrderNo())){
            throw new BusinessException("订单号为空！");
        }
        SysBusParameter type = sysBusParameterService.getByCode(String.valueOf(PayConstants.RECHARGE_TYPE));
        if(type==null){
            throw new BusinessException("交易类型未初始化！");
        }
        MemGoldchangeVO  memGoldchangeVO =payOrderRechargeService.getByAccountAndOrderNo(user.getUserAccount(),req.getOrderNo());
        memGoldchangeVO.setTransactionTypeName(type.getParamValue());
        return memGoldchangeVO;
    }
}
