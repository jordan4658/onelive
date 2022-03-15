package com.onelive.manage.modules.finance.business;


import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.PayExchangeRateCfgAddReq;
import com.onelive.common.model.req.pay.PayExchangeRateCfgUpdateReq;
import com.onelive.common.model.vo.pay.PayExchangeRateCfgVO;
import com.onelive.common.mybatis.entity.PayExchangeRateCfg;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.finance.PayExchangeRateCfgService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lorenzo
 * @Description:
 * @date 2021/4/2
 */
@Component
public class PayExchangeRateCfgBusiness {

    @Resource
    private PayExchangeRateCfgService payExchangeRateCfgService;

    /**
     * 查询汇率配置列表信息
     *
     * @param pageSize
     * @param pageNum
     * @param currencyCode
     * @return
     */
    public PageInfo<PayExchangeRateCfgVO> pageList(Integer pageSize, Integer pageNum, String currencyCode) {
        return payExchangeRateCfgService.pageList(pageSize, pageNum, currencyCode);
    }


    public void save(PayExchangeRateCfgAddReq req, LoginUser loginAdmin) {
        if (StringUtils.isEmpty(req.getCurrencyCode())) {
            throw new BusinessException("国家编码不能为空！");
        }
        if (StringUtils.isEmpty(req.getCzFloatingValue())) {
            throw new BusinessException("充值(转换)汇率浮动值(百分比)不能为空！");
        }
        if (StringUtils.isEmpty(req.getTxFloatingValue())) {
            throw new BusinessException("提现汇率浮动值(百分比)不能为空！");
        }
        //检查 国家编码是否已存在!
        Integer count = payExchangeRateCfgService.selectCount(req.getCurrencyCode());
        if (count > 0) {
            throw new BusinessException("国家编码已存在，请勿重复添加！");
        }
        PayExchangeRateCfg rateCfg = new PayExchangeRateCfg();
        Date date = new Date();
        BeanUtils.copyProperties(req, rateCfg);
        rateCfg.setCreateUser(loginAdmin.getAccLogin());
        rateCfg.setUpdateUser(loginAdmin.getAccLogin());
        rateCfg.setCreateTime(date);
        rateCfg.setUpdateTime(date);
        rateCfg.setIsDelete(false);
        payExchangeRateCfgService.save(rateCfg);
    }

    public void update(PayExchangeRateCfgUpdateReq req, LoginUser loginAdmin) {
        if (req.getExchangeRateCfgId() == null) {
            throw new BusinessException("汇率配置ID不能为空！！");
        }
        if (StringUtils.isEmpty(req.getCurrencyCode())) {
            throw new BusinessException("国家编码不能为空！");
        }
        if (StringUtils.isEmpty(req.getCzFloatingValue())) {
            throw new BusinessException("充值(转换)汇率浮动值(百分比)不能为空！");
        }
        if (StringUtils.isEmpty(req.getTxFloatingValue())) {
            throw new BusinessException("提现汇率浮动值(百分比)不能为空！");
        }
        PayExchangeRateCfg rateCfg = payExchangeRateCfgService.getById(req.getExchangeRateCfgId());
        if (rateCfg == null) {
            throw new BusinessException("更新的信息不存在！");
        }
        if (!rateCfg.getCurrencyCode().equals(req.getCurrencyCode())) {
            //检查 国家编码是否已存在!
            Integer count = payExchangeRateCfgService.selectCount(req.getCurrencyCode());
            if (count > 0) {
                throw new BusinessException("国家编码已存在，请勿重复添加！");
            }
        }
        Date date = new Date();
        BeanUtils.copyProperties(req, rateCfg);
        rateCfg.setUpdateUser(loginAdmin.getAccLogin());
        rateCfg.setUpdateTime(date);
        payExchangeRateCfgService.updateById(rateCfg);
    }

    public void delete(List<Long> ids, LoginUser loginAdmin) {
        for (Long id : ids) {
            PayExchangeRateCfg rateCfg = payExchangeRateCfgService.getById(id);
            if (rateCfg != null) {
                rateCfg.setIsDelete(true);
                rateCfg.setUpdateUser(loginAdmin.getAccLogin());
                rateCfg.setUpdateTime(new Date());
                payExchangeRateCfgService.updateById(rateCfg);
            }
        }
    }
}
