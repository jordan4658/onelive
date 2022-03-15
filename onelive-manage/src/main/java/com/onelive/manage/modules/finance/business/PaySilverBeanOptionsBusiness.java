package com.onelive.manage.modules.finance.business;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.*;
import com.onelive.common.model.vo.pay.PaySilverBeanOptionsByIdVO;
import com.onelive.common.model.vo.pay.PaySilverBeanOptionsVO;
import com.onelive.common.mybatis.entity.PaySilverBeanOptions;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.pay.PayUtils;
import com.onelive.common.utils.pay.RandomUtil;
import com.onelive.manage.service.finance.PaySilverBeanOptionsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayWayBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/16 16:58
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PaySilverBeanOptionsBusiness {

    @Resource
    private PaySilverBeanOptionsService paySilverBeanOptionsService;


    /**
     * 查询 支付快捷选项列表
     *
     * @param pageNum
     * @param pageSize
     * @param isEnable
     * @return
     */
    public PageInfo<PaySilverBeanOptionsVO> listPage(Integer pageNum, Integer pageSize, Boolean isEnable) {
        PageHelper.startPage(pageNum, pageSize);
        List<PaySilverBeanOptionsVO> list = paySilverBeanOptionsService.listPage(isEnable);
        return new PageInfo<PaySilverBeanOptionsVO>(list);
    }

    /**
     * 新增-支付快捷选项信息
     *
     * @param user
     */
    public void add(PayWithdrawalOptionsAddReq req, LoginUser user) {
        if (req == null) {
            throw new BusinessException("快捷选项信息为空！");
        }
        for (String str : req.getOptionsContent().split(",")) {
            if (!PayUtils.strVerifyNumber(str)) {
                throw new BusinessException("快捷提现-填写错误！");
            }
        }
        PaySilverBeanOptions payWithdrawalOptions = new PaySilverBeanOptions();
        Date date = new Date();
        payWithdrawalOptions.setIsEnable(true);
        payWithdrawalOptions.setIsDelete(false);
        payWithdrawalOptions.setOptionsContent(req.getOptionsContent());
        payWithdrawalOptions.setCreateUser(user.getAccLogin());
        payWithdrawalOptions.setUpdateUser(user.getAccLogin());
        payWithdrawalOptions.setCreateTime(date);
        payWithdrawalOptions.setUpdateTime(date);
        paySilverBeanOptionsService.save(payWithdrawalOptions);
    }

    /**
     * 根据支付方式id更新支付方式信息
     *
     * @param req
     * @param user
     */
    public void update(PayWithdrawalOptionsUpdateReq req, LoginUser user) {

        if (req == null) {
            throw new BusinessException("兑换快捷选项信息为空！");
        }
        if (req.getSilverBeanOptionsId()==null) {
            throw new BusinessException("兑换选项ID为空！");
        }
        for (String str : req.getOptionsContent().split(",")) {
            if (!PayUtils.strVerifyNumber(str)) {
                throw new BusinessException("快捷充值-填写错误！");
            }
        }
        PaySilverBeanOptions paySilverBeanOptions = new PaySilverBeanOptions();
        BeanUtils.copyProperties(req, paySilverBeanOptions);
        Date date = new Date();
        UpdateWrapper<PaySilverBeanOptions> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda()
                .set(PaySilverBeanOptions::getOptionsContent, req.getOptionsContent())
                .set(PaySilverBeanOptions::getUpdateUser, user.getAccLogin())
                .set(PaySilverBeanOptions::getUpdateTime, date)
                .eq(PaySilverBeanOptions::getSilverBeanOptionsId, req.getSilverBeanOptionsId());
        paySilverBeanOptionsService.update(updateWrapper);
    }


    /**
     * 禁用-启用 支付快捷选项
     *
     * @param req
     * @param user
     */
    public void updateEnable(PayWithdrawalOptionsIsEnableReq req, LoginUser user) {
        if (CollectionUtil.isEmpty(req.getSilverBeanOptionsIds())) {
            throw new BusinessException("快捷选项ID！");
        }
        if (req.getIsEnable() == null) {
            throw new BusinessException("状态为空！");
        }
        UpdateWrapper<PaySilverBeanOptions> updateWrapper = new UpdateWrapper<PaySilverBeanOptions>();
        updateWrapper.lambda()
                .set(PaySilverBeanOptions::getIsEnable, req.getIsEnable())
                .set(PaySilverBeanOptions::getUpdateUser, user.getAccLogin())
                .set(PaySilverBeanOptions::getUpdateTime, new Date())
                .in(PaySilverBeanOptions::getSilverBeanOptionsId, req.getSilverBeanOptionsIds());
        paySilverBeanOptionsService.update(updateWrapper);
    }


    public PaySilverBeanOptionsByIdVO getById(Long id) {
        if (id == null) {
            throw new BusinessException("快捷选项ID为空！");
        }
        PaySilverBeanOptions options = paySilverBeanOptionsService.getById(id);
        if (options == null) {
            throw new BusinessException("快捷选项ID错误！");
        }
        PaySilverBeanOptionsByIdVO vo = new PaySilverBeanOptionsByIdVO();
        BeanUtils.copyProperties(options, vo);
        return vo;
    }

    public void delete(PayWithdrawalOptionsDeleteReq req, LoginUser user) {
        if (CollectionUtil.isEmpty(req.getSilverBeanOptionsIds())) {
            throw new BusinessException("数据ID为空！");
        }
        UpdateWrapper<PaySilverBeanOptions> updateWrapper = new UpdateWrapper<PaySilverBeanOptions>();
        updateWrapper.lambda()
                .set(PaySilverBeanOptions::getIsDelete, true)
                .set(PaySilverBeanOptions::getUpdateUser, user.getAccLogin())
                .set(PaySilverBeanOptions::getUpdateTime, new Date())
                .in(PaySilverBeanOptions::getSilverBeanOptionsId, req.getSilverBeanOptionsIds());
        paySilverBeanOptionsService.update(updateWrapper);

    }
}