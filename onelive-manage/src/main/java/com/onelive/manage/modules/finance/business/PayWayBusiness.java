package com.onelive.manage.modules.finance.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.PayWayBackAddReq;
import com.onelive.common.model.req.pay.PayWayBackUpReq;
import com.onelive.common.model.req.pay.PayWayBackUpStatusReq;
import com.onelive.common.model.vo.pay.PayWayBackVO;
import com.onelive.common.model.vo.pay.PayWaySelectVO;
import com.onelive.common.mybatis.entity.PayWay;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.pay.PayUtils;
import com.onelive.manage.service.finance.PayWayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
public class PayWayBusiness {

    @Resource
    private PayWayService payWayService;


    /**
     * 参数校验
     *
     * @param payWay
     */
    private void parameterVerify(PayWay payWay) {
        if (payWay.getIsInput() == null) {
            throw new BusinessException("是否允许输入金额为空！");
        }
        if (payWay.getSortBy() == null) {
            throw new BusinessException("排序字段错误！");
        }
        if (StringUtils.isBlank(payWay.getPayWayName())) {
            throw new BusinessException("支付方式名称为空！");
        }
        if (StringUtils.isBlank(payWay.getPayWayTag())) {
            throw new BusinessException("支付标识为空！");
        }
        if (0 != payWay.getGivingType()) {
            if (payWay.getPayWayGivingRatio() == null || payWay.getPayWayGivingRatio().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("赠送比例为空或小于零！");
            }
        } else {
            payWay.setPayWayGivingRatio(new BigDecimal("0.0000"));
        }

        if (payWay.getGivingType() == null) {
            throw new BusinessException("赠送类型为空！");
        }
        Boolean givingFlag = true;
        for (PayConstants.givingTypeEnum givingType : PayConstants.givingTypeEnum.values()) {
            if (givingType.getCode() == payWay.getGivingType()) {
                givingFlag = false;
                break;
            }
        }
        if (givingFlag) {
            throw new BusinessException("赠送类型错误！");
        }
        if (payWay.getPayTypeCode() == null) {
            throw new BusinessException("支付类型为空！");
        }
        Boolean typeCodeFlag = true;
        for (PayConstants.PayTypeEnum payTypeEnum : PayConstants.PayTypeEnum.values()) {
            if (payTypeEnum.getCode() == payWay.getPayTypeCode()) {
                typeCodeFlag = false;
                break;
            }
        }
        if (typeCodeFlag) {
            throw new BusinessException("支付类型错误！");
        }
        if (payWay.getProviderId() == null) {
            throw new BusinessException("支付商为空！");
        }
//        if (payWay.getIsInput() == true) {
//            if (StringUtils.isBlank(payWay.getShortcut())) {
//                throw new BusinessException("快捷充值为空！");
//            }
//        }
//        for (String str : payWay.getShortcut().split(",")) {
//            if (!PayUtils.strVerifyNumber(str)) {
//                throw new BusinessException("快捷充值-填写错误！");
//            }
//        }
        if (payWay.getMinAmt() == null || payWay.getMinAmt().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("单笔最低充值金额为空或小于等于零！");
        }
        if (payWay.getMaxAmt() == null || payWay.getMaxAmt().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("单笔最高充值金额为空 或者，小于或等于零！！");
        }
        if (payWay.getMaxAmt().compareTo(payWay.getMinAmt()) <= 0) {
            throw new BusinessException("单笔最高充值金额小于或等于最低充值金额！");
        }
    }


    /**
     * 查询-支付方式列表 分页
     *
     * @param pageNum
     * @param pageSize
     * @param payWayName
     * @param payTypeCode
     * @param status
     * @param user
     * @return
     */
    public PageInfo<PayWayBackVO> listPage(Integer pageNum, Integer pageSize, String payWayName,String countryCode, String payTypeCode, Integer status, LoginUser user, Integer providerType) {
        PageHelper.startPage(pageNum, pageSize);
        List<PayWayBackVO> list = payWayService.listPage(payWayName, payTypeCode, countryCode, status, providerType);
        return new PageInfo<PayWayBackVO>(list);
    }

    /**
     * 新增-支付方式
     *
     * @param payWayBackAddReq
     * @param user
     */
    public void add(PayWayBackAddReq payWayBackAddReq, LoginUser user) {
        if (payWayBackAddReq == null) {
            throw new BusinessException("支付方式信息为空！");
        }
        PayWay payWay = new PayWay();
        BeanUtils.copyProperties(payWayBackAddReq, payWay);
        parameterVerify(payWay);
        Date date = new Date();
        payWay.setCreateUser(user.getAccLogin());
        payWay.setUpdateUser(user.getAccLogin());
        payWay.setCreateTime(date);
        payWay.setUpdateTime(date);
        payWayService.add(payWay);
    }

    /**
     * 根据支付方式id更新支付方式信息
     *
     * @param payWayBackUpReq
     * @param user
     */
    public void update(PayWayBackUpReq payWayBackUpReq, LoginUser user) {

        if (payWayBackUpReq == null) {
            throw new BusinessException("支付方式信息为空！");
        }
        if (payWayBackUpReq.getPayWayId() == null) {
            throw new BusinessException("支付方式ID为空！");
        }
        if (payWayBackUpReq.getStatus() == null) {
            throw new BusinessException("支付方式状态为空！");
        }
        PayWay payWay = new PayWay();
        BeanUtils.copyProperties(payWayBackUpReq, payWay);
        parameterVerify(payWay);
        Date date = new Date();
        payWay.setCreateUser(user.getAccLogin());
        payWay.setUpdateUser(user.getAccLogin());
        payWay.setCreateTime(date);
        payWay.setUpdateTime(date);
        payWayService.updateById(payWay);
    }


    /**
     * 禁用-启用 支付方式
     *
     * @param payWayBackUpStatusReq
     * @param user
     */
    public void updateEnable(PayWayBackUpStatusReq payWayBackUpStatusReq, LoginUser user) {
        if (payWayBackUpStatusReq == null) {
            throw new BusinessException("支付方式信息为空！");
        }
        if (payWayBackUpStatusReq.getPayWayId() == null) {
            throw new BusinessException("支付方式Id为空！");
        }
        if (payWayBackUpStatusReq.getStatus() == null) {
            throw new BusinessException("支付方式状态为空！");
        }
        Boolean flag = true;
        for (PayConstants.StatusEnum statusEnum : PayConstants.StatusEnum.values()) {
            if (statusEnum.getCode() == payWayBackUpStatusReq.getStatus()) {
                flag = false;
                break;
            }
        }
        if (flag) {
            throw new BusinessException("支付状态错误！！");
        }
        PayWay payWay = new PayWay();
        payWay.setPayWayId(payWayBackUpStatusReq.getPayWayId());
        payWay.setStatus(payWayBackUpStatusReq.getStatus());
        payWay.setUpdateUser(user.getAccLogin());
        payWay.setUpdateTime(new Date());
        payWayService.updateById(payWay);
    }


    public List<PayWaySelectVO> select(String countryCode) {
        QueryWrapper<PayWay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PayWay::getStatus, 1);
        queryWrapper.lambda().eq(PayWay::getCountryCode, countryCode);
        List<PayWay> list = payWayService.list(queryWrapper);
        List<PayWaySelectVO> voList = BeanCopyUtil.copyCollection(list, PayWaySelectVO.class);

        return voList;
    }
}