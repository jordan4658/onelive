package com.onelive.manage.modules.finance.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.PayTypeBackAddReq;
import com.onelive.common.model.req.pay.PayTypeBackUpReq;
import com.onelive.common.model.vo.pay.PayTypeBackVO;
import com.onelive.common.model.vo.pay.PayTypeSelectVO;
import com.onelive.common.mybatis.entity.PayType;
import com.onelive.manage.service.finance.PayTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayTypeBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/16 19:00
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PayTypeBusiness {

    @Resource
    private PayTypeService payTypeService;

    /**
     * 查询支付类型列表 分页
     *
     * @param user
     * @param pageNum
     * @param pageSize
     * @param payTypeName
     * @return
     */
    public PageInfo<PayTypeBackVO> listPage(LoginUser user, Integer pageNum, Integer pageSize, String payTypeName) {
        PageHelper.startPage(pageNum, pageSize);
        List<PayTypeBackVO> list = payTypeService.listPage(payTypeName);
        return new PageInfo<PayTypeBackVO>(list);
    }

    /**
     * 支付类型新增
     *
     * @param payTypeBackAddReq
     * @param user
     * @return
     */
    public void add(PayTypeBackAddReq payTypeBackAddReq, LoginUser user) {
        if (StringUtils.isBlank(payTypeBackAddReq.getPayTypeName())) {
            throw new BusinessException("支付类型名称为空！");
        }
        if (payTypeBackAddReq.getPayTypeCode() == null) {
            throw new BusinessException("支付类型code为空！");
        }
        if (payTypeBackAddReq.getPayTypeCode() == null) {
            throw new BusinessException("支付类型code为空！");
        }
        if (payTypeBackAddReq.getIsHot() == null) {
            throw new BusinessException("是否热门为空！");
        }
        PayType payType = new PayType();
        Date date = new Date();
        payType.setPayTypeId(null);
        payType.setPayTypeCode(payTypeBackAddReq.getPayTypeCode());
        payType.setPayTypeName(payTypeBackAddReq.getPayTypeName());
        payType.setIsHot(payTypeBackAddReq.getIsHot());
        payType.setIconUrl(payTypeBackAddReq.getIconUrl());
        payType.setCreateUser(user.getAccLogin());
        payType.setUpdateUser(user.getAccLogin());
        payType.setCreateTime(date);
        payType.setUpdateTime(date);
        payTypeService.save(payType);
    }

    /**
     * 根据支付类型id更新 名称 状态
     *
     * @param payTypeBackUpReq
     * @param user
     */
    public void update(PayTypeBackUpReq payTypeBackUpReq, LoginUser user) {
        if (payTypeBackUpReq.getPayTypeId() == null) {
            throw new BusinessException("支付类型Id为空！");
        }
        if (StringUtils.isBlank(payTypeBackUpReq.getPayTypeName()) && payTypeBackUpReq.getIsEnable() == null) {
            log.error("更新支付类型 参数为空！");
            return;
        }
        if (payTypeBackUpReq.getIsHot() == null) {
            throw new BusinessException("是否热门为空！");
        }
        PayType payType = new PayType();
        payType.setPayTypeId(payTypeBackUpReq.getPayTypeId());
        payType.setPayTypeName(payTypeBackUpReq.getPayTypeName());
        payType.setIsEnable(payTypeBackUpReq.getIsEnable());
        payType.setIsHot(payTypeBackUpReq.getIsHot());
        payType.setUpdateUser(user.getAccLogin());
        payType.setIconUrl(payTypeBackUpReq.getIconUrl());
        payType.setUpdateTime(new Date());
        payTypeService.updateById(payType);
    }

    /**
     * 查询支付类型
     * @return
     */
    public List<PayTypeSelectVO> select() {
        return payTypeService.select();
    }
}
