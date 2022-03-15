package com.onelive.manage.modules.finance.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.req.pay.PayHandleFinanceAmtBackReq;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.model.vo.pay.PayHandleFinanceAmtBackVO;
import com.onelive.common.mybatis.entity.PayHandleFinanceAmt;
import com.onelive.common.utils.pay.PayUtils;
import com.onelive.manage.common.constant.SystemRedisKeys;
import com.onelive.manage.service.finance.PayHandleFinanceAmtService;
import com.onelive.manage.service.mem.MemUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayHandleFinanceAmtBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/19 12:04
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PayHandleFinanceAmtBusiness {

    @Resource
    private PayHandleFinanceAmtService payHandleFinanceAmtService;
    @Resource
    private MemUserService memUserService;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private AccountBalanceChangeService accountBalanceChangeService;

    public PageInfo<PayHandleFinanceAmtBackVO> listPage(Integer pageNum, Integer pageSize, String nickname, String account, LoginUser user,String accno) {
        PageHelper.startPage(pageNum, pageSize);
        List<PayHandleFinanceAmtBackVO> list = payHandleFinanceAmtService.listPage(nickname, account,accno);
        return new PageInfo<PayHandleFinanceAmtBackVO>(list);
    }

    @Transactional
    public void addHandleFinanceAmt(PayHandleFinanceAmtBackReq payHandleFinanceAmtBackReq, LoginUser user) {
        if (payHandleFinanceAmtBackReq == null) {
            throw new BusinessException("加减款/加减码信息为空！");
        }
        if (StringUtils.isBlank(payHandleFinanceAmtBackReq.getAccount())) {
            throw new BusinessException("会员账号为空！");
        }
        if (StringUtils.isBlank(payHandleFinanceAmtBackReq.getHandleExplain())) {
            throw new BusinessException("操作说明为空！");
        }
        if (payHandleFinanceAmtBackReq.getHandleNum() == null || payHandleFinanceAmtBackReq.getHandleNum().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("金额错误！");
        }
        if ( Integer.valueOf(payHandleFinanceAmtBackReq.getHandleNum().intValue()).toString().length()>12) {
            throw new BusinessException("输入的金额不能超过12位！");
        }
        if (payHandleFinanceAmtBackReq.getHandleType() == null) {
            throw new BusinessException("处理类型为空！");
        } else {
            //处理类型：10-减款、11-加款、12-减码、13-加码
            if (PayConstants.AccountChangTypeEnum.CHANG_TYPE10.getPayTypeCode() == payHandleFinanceAmtBackReq.getHandleType() ||
                    PayConstants.AccountChangTypeEnum.CHANG_TYPE11.getPayTypeCode() == payHandleFinanceAmtBackReq.getHandleType()) {
                if (payHandleFinanceAmtBackReq.getHandleNumType() == null) {
                    throw new BusinessException("金额类型为空！");
                }
            }
        }

        if (payHandleFinanceAmtBackReq.getIsDm() == null) {
            throw new BusinessException("是否进行打码为空！");
        }
        Boolean flag = true;
        String orderHead = "";
        for (PayConstants.HandleTypeEnum typeEnum : PayConstants.HandleTypeEnum.values()) {
            if (typeEnum.getCode() == payHandleFinanceAmtBackReq.getHandleType()) {
                flag = false;
                orderHead = typeEnum.getValue();
                break;
            }
        }
        if (flag) {
            throw new BusinessException("处理类型不对！");
        }
        //redis写锁
        //每次账变前先对该用户加上账变写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock(SystemRedisKeys.PAY_MANAGE_ARTIFICIAL_ADD_SUB_AMT_DML + payHandleFinanceAmtBackReq.getAccount());
        try {
            // 写锁（等待时间20s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
            boolean bool = lock.writeLock().tryLock(20, 10, TimeUnit.SECONDS);
            //是否拿到用户账变写锁
            if (bool) {
                //检查处理的账号是否存在
                if (!memUserService.isExistByAccount(payHandleFinanceAmtBackReq.getAccount())) {
                        throw new BusinessException("用户不存在！");
                }

                //添加 人工操作记录表
                PayHandleFinanceAmt payHandleFinanceAmt = new PayHandleFinanceAmt();
                BeanUtils.copyProperties(payHandleFinanceAmtBackReq, payHandleFinanceAmt);
                String orderNo = orderHead + PayUtils.getOrderNo();
                payHandleFinanceAmt.setCreateUser(user.getAccLogin());
                payHandleFinanceAmt.setUpdateUser(user.getAccLogin());
                payHandleFinanceAmt.setHandleOrderNo(orderNo);
                payHandleFinanceAmt.setIsDm(payHandleFinanceAmtBackReq.getIsDm());
                payHandleFinanceAmtService.save(payHandleFinanceAmt);
                //用户公共账变
                MemAccountChangeVO changeVO = new MemAccountChangeVO();
                changeVO.setAccount(payHandleFinanceAmtBackReq.getAccount());
                changeVO.setChangeType(payHandleFinanceAmtBackReq.getHandleType());
                changeVO.setFlowType(PayConstants.FlowTypeEnum.INCOME.getCode());
                changeVO.setOrderNo(orderNo);
                changeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
                if (payHandleFinanceAmtBackReq.getHandleType() == PayConstants.HandleTypeEnum.ADD_DML.getCode()) {
                    changeVO.setPrice(new BigDecimal("0.0000"));
                    changeVO.setDml(payHandleFinanceAmtBackReq.getHandleNum());
                } else if (payHandleFinanceAmtBackReq.getHandleType() == PayConstants.HandleTypeEnum.SUBTRACT_DML.getCode()) {
                    changeVO.setPrice(new BigDecimal("0.0000"));
                    changeVO.setDml(payHandleFinanceAmtBackReq.getHandleNum().multiply(new BigDecimal("-1.0000")));
                } else if (payHandleFinanceAmtBackReq.getHandleType() == PayConstants.HandleTypeEnum.ADD_AMT.getCode()) {
                    //金额类型：1-银豆、2-金币
                    if (payHandleFinanceAmtBackReq.getHandleNumType() == 1) {
                        changeVO.setSilverBeanPrice(payHandleFinanceAmtBackReq.getHandleNum());
                    } else {
                        changeVO.setPrice(payHandleFinanceAmtBackReq.getHandleNum());
                    }
                    changeVO.setDml(new BigDecimal("0.0000"));
                    if (payHandleFinanceAmtBackReq.getIsDm()) {
                        changeVO.setDml(payHandleFinanceAmtBackReq.getHandleNum());
                    }
                } else if (payHandleFinanceAmtBackReq.getHandleType() == PayConstants.HandleTypeEnum.SUBTRACT_AMT.getCode()) {
                    //金额类型：1-银豆、2-金币
                    if (payHandleFinanceAmtBackReq.getHandleNumType() == 1) {
                        changeVO.setSilverBeanPrice(payHandleFinanceAmtBackReq.getHandleNum().multiply(new BigDecimal("-1.0000")));
                    } else {
                        changeVO.setPrice(payHandleFinanceAmtBackReq.getHandleNum().multiply(new BigDecimal("-1.0000")));
                    }
                    changeVO.setDml(new BigDecimal("0.0000"));
                }
                changeVO.setOpNote(payHandleFinanceAmtBackReq.getHandleExplain());
                //处理类型：10-减款、11-加款、12-减码、13-加码
                if (PayConstants.AccountChangTypeEnum.CHANG_TYPE10.getPayTypeCode() == payHandleFinanceAmtBackReq.getHandleType() ||
                        PayConstants.AccountChangTypeEnum.CHANG_TYPE11.getPayTypeCode() == payHandleFinanceAmtBackReq.getHandleType()) {
                    //金额类型：1-银豆、2-金币
                    if (payHandleFinanceAmtBackReq.getHandleNumType() == 1) {
                        changeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_1.getCode());
                    } else {
                        changeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
                    }
                }
                BalanceChangeDTO balanceChangeDTO = accountBalanceChangeService.publicAccountBalanceChange(changeVO);
                if (!balanceChangeDTO.getFlag()) {
                    log.error("公共账变失败！");
                    throw new BusinessException();
                }
            } else {
                throw new BusinessException("公共账变正在处理中-会员账号：" + payHandleFinanceAmtBackReq.getAccount() + " 正在处理中，请勿重复处理！");
            }

        } catch (Exception e) {
            log.error("{} addHandleFinanceAmt occur error. change info:{}", payHandleFinanceAmtBackReq.getAccount(), payHandleFinanceAmtBackReq, e);
            throw new BusinessException(e.getMessage(), e.getCause());
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("{} addHandleFinanceAmt 人工加减款/加减码 释放锁", payHandleFinanceAmtBackReq.getAccount());
        }

    }
}
