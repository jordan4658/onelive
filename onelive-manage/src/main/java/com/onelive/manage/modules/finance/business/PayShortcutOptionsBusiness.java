package com.onelive.manage.modules.finance.business;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.*;
import com.onelive.common.model.vo.pay.PayShortcutOptionsByIdVO;
import com.onelive.common.model.vo.pay.PayShortcutOptionsVO;
import com.onelive.common.model.vo.pay.PayWayBackVO;
import com.onelive.common.model.vo.pay.UnitAndExChangerVO;
import com.onelive.common.mybatis.entity.PayExchangeCurrency;
import com.onelive.common.mybatis.entity.PayShortcutOptions;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.pay.PayUtils;
import com.onelive.common.utils.pay.RandomUtil;
import com.onelive.manage.service.finance.PayExchangeCurrencyService;
import com.onelive.manage.service.finance.PayShortcutOptionsService;
import com.onelive.manage.service.sys.SysCountryService;
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
public class PayShortcutOptionsBusiness {

    @Resource
    private PayShortcutOptionsService payShortcutOptionsService;
    @Resource
    private SysCountryService sysCountryService;
    @Resource
    private PayExchangeCurrencyService payExchangeCurrencyService;


    /**
     * 查询 支付快捷选项列表
     *
     * @param pageNum
     * @param pageSize
     * @param payWayId
     * @param isEnable
     * @return
     */
    public PageInfo<PayShortcutOptionsVO> listPage(Integer pageNum, Integer pageSize, Long payWayId, Boolean isEnable,String countryCode) {
        PageHelper.startPage(pageNum, pageSize);
        List<PayShortcutOptionsVO> list = payShortcutOptionsService.listPage(payWayId, isEnable,countryCode);
        return new PageInfo<PayShortcutOptionsVO>(list);
    }

    /**
     * 新增-支付快捷选项信息
     *
     * @param user
     */
    public void add(PayShortcutOptionsAddReq req, LoginUser user) {
        if (req == null) {
            throw new BusinessException("支付快捷选项信息为空！");
        }
        if (req.getPayWayId() == null) {
            throw new BusinessException("支付方式ID为空！");
        }
        for (String str : req.getShortcutOptionsContent().split(",")) {
            if (!PayUtils.strVerifyNumber(str)) {
                throw new BusinessException("快捷充值-填写错误！");
            }
        }
        PayShortcutOptions payShortcutOptions = new PayShortcutOptions();
        BeanUtils.copyProperties(req, payShortcutOptions);
        Date date = new Date();
        payShortcutOptions.setPayWayId(req.getPayWayId());
        payShortcutOptions.setIsEnable(true);
        payShortcutOptions.setIsDelete(false);
        payShortcutOptions.setShortcutOptionsContent(req.getShortcutOptionsContent());
        payShortcutOptions.setCreateUser(user.getAccLogin());
        payShortcutOptions.setUpdateUser(user.getAccLogin());
        payShortcutOptions.setCreateTime(date);
        payShortcutOptions.setUpdateTime(date);
        payShortcutOptionsService.save(payShortcutOptions);

    }

    /**
     * 根据支付方式id更新支付方式信息
     *
     * @param req
     * @param user
     */
    public void update(PayShortcutOptionsUpdateReq req, LoginUser user) {

        if (req == null) {
            throw new BusinessException("支付快捷选项信息为空！");
        }
        if (req.getPayWayId() == null) {
            throw new BusinessException("支付方式ID为空！");
        }
        for (String str : req.getShortcutOptionsContent().split(",")) {
            if (!PayUtils.strVerifyNumber(str)) {
                throw new BusinessException("快捷充值-填写错误！");
            }
        }
        PayShortcutOptions payShortcutOptions = new PayShortcutOptions();
        BeanUtils.copyProperties(req, payShortcutOptions);
        Date date = new Date();
        UpdateWrapper<PayShortcutOptions> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda()
                .set(PayShortcutOptions::getPayWayId, req.getPayWayId())
                .set(PayShortcutOptions::getShortcutOptionsContent, req.getShortcutOptionsContent())
                .set(PayShortcutOptions::getUpdateUser, user.getAccLogin())
                .set(PayShortcutOptions::getUpdateTime, date)
                .eq(PayShortcutOptions::getShortcutOptionsId, req.getShortcutOptionsId());
        payShortcutOptionsService.update(updateWrapper);
    }


    /**
     * 禁用-启用 支付快捷选项
     *
     * @param req
     * @param user
     */
    public void updateEnable(PayShortcutOptionsIsEnableReq req, LoginUser user) {
        if (CollectionUtil.isEmpty(req.getShortcutOptionsIds())) {
            throw new BusinessException("快捷选项ID为空！");
        }
        if (req.getIsEnable() == null) {
            throw new BusinessException("状态为空！");
        }
        UpdateWrapper<PayShortcutOptions> updateWrapper = new UpdateWrapper<PayShortcutOptions>();
        updateWrapper.lambda()
                .set(PayShortcutOptions::getIsEnable, req.getIsEnable())
                .set(PayShortcutOptions::getUpdateUser, user.getAccLogin())
                .set(PayShortcutOptions::getUpdateTime, new Date())
                .in(PayShortcutOptions::getShortcutOptionsId, req.getShortcutOptionsIds());
        payShortcutOptionsService.update(updateWrapper);
    }


    public PayShortcutOptionsByIdVO getById(Long id) {
        if (id == null) {
            throw new BusinessException("支付快捷选项ID为空！");
        }
        PayShortcutOptionsByIdVO vo = payShortcutOptionsService.selectShortcutOptionsById(id);
        if (vo == null) {
            throw new BusinessException("支付快捷选项ID错误！");
        }
        return vo;
    }

    public void delete(PayShortcutOptionsDeleteReq req, LoginUser user) {
        if (CollectionUtil.isEmpty(req.getShortcutOptionsIds())) {
            throw new BusinessException("快捷选项ID为空！");
        }
        UpdateWrapper<PayShortcutOptions> updateWrapper = new UpdateWrapper<PayShortcutOptions>();
        updateWrapper.lambda()
                .set(PayShortcutOptions::getIsDelete, true)
                .set(PayShortcutOptions::getUpdateUser, user.getAccLogin())
                .set(PayShortcutOptions::getUpdateTime, new Date())
                .in(PayShortcutOptions::getShortcutOptionsId, req.getShortcutOptionsIds());
        payShortcutOptionsService.update(updateWrapper);

    }

    public UnitAndExChangerVO getUnitAndExChanger(String countryCode) {
        SysCountry sysCountry = sysCountryService.getByCountryCode(countryCode);
        if(sysCountry==null){
            throw new BusinessException("未查询到： countryCode 国家信息！");
        }
        PayExchangeCurrency exchangeCurrency = payExchangeCurrencyService.selectByCurrencyCode(sysCountry.getLocalCurrency());
        if(exchangeCurrency==null){
            throw new BusinessException("未查询到："+sysCountry.getZhName()+" 的汇率信息！");
        }
        UnitAndExChangerVO vo=new UnitAndExChangerVO();
        vo.setUnit(exchangeCurrency.getCurrencyUnit());
        vo.setReExChanger(exchangeCurrency.getCzExchange());
        return vo;
    }
}