package com.onelive.manage.modules.finance.business;


import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.PayFindExchangeCfgAddReq;
import com.onelive.common.model.req.pay.PayFindExchangeCfgUpdateReq;
import com.onelive.common.model.vo.pay.PayFindExchangeCfgVO;
import com.onelive.common.model.vo.pay.QueryExchangeVO;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.common.mybatis.entity.PayFindExchangeCfg;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.finance.PayFindExchangeCfgService;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.utils.other.StringInnerUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PayFindExchangeCfgBusiness {

    @Resource
    private PayFindExchangeCfgService payFindExchangeCfgService;

    @Resource
    private SysParameterService sysParameterService;

    public PageInfo<PayFindExchangeCfgVO> pageList(Integer pageSize, Integer pageNum, String exchangeKey, String exchangeDataSourceCode ) {
        return payFindExchangeCfgService.pageList(pageSize, pageNum, exchangeKey,exchangeDataSourceCode);
    }


    public void save(PayFindExchangeCfgAddReq req, LoginUser loginAdmin) {
        if (StringUtils.isEmpty(req.getExchangeUrlCode())) {
            throw new BusinessException("查询汇率的code不能为空！");
        }
        if (StringUtils.isEmpty(req.getExchangeKey())) {
            throw new BusinessException("请求Key不能为空！");
        }
        //查询 获取汇率code参数信息
        SysParameter parameterByCodeVO = sysParameterService.getByCode(req.getExchangeUrlCode());
        if (parameterByCodeVO == null) {
            throw new BusinessException("查询汇率的code 查询不到对应的系统参数信息！");
        }
        PayFindExchangeCfg findExchangeCfg = new PayFindExchangeCfg();
        Date date = new Date();
        BeanUtils.copyProperties(req, findExchangeCfg);
        findExchangeCfg.setExchangeUrl(parameterByCodeVO.getParamValue());
        if (StringUtils.isNotEmpty(parameterByCodeVO.getRemark()) && StringInnerUtils.isNumeric(parameterByCodeVO.getRemark())) {
            findExchangeCfg.setFrequency(Integer.valueOf(parameterByCodeVO.getRemark()));
        }
        findExchangeCfg.setCreateTime(date);
        findExchangeCfg.setUpdateTime(date);
        findExchangeCfg.setCreateUser(loginAdmin.getAccLogin());
        findExchangeCfg.setUpdateUser(loginAdmin.getAccLogin());
        findExchangeCfg.setIsDelete(false);
        payFindExchangeCfgService.save(findExchangeCfg);
    }

    public void update(PayFindExchangeCfgUpdateReq req, LoginUser loginAdmin) {
        if (req.getExchangeKeyId() == null) {
            throw new BusinessException("汇率查询keyID不能为空！");
        }
        if (StringUtils.isEmpty(req.getExchangeUrlCode())) {
            throw new BusinessException("查询汇率的code不能为空！");
        }
        if (StringUtils.isEmpty(req.getExchangeKey())) {
            throw new BusinessException("请求Key不能为空！");
        }
        //查询 获取汇率code参数信息
        SysParameter parameterByCodeVO = sysParameterService.getByCode(req.getExchangeUrlCode());
        if (parameterByCodeVO == null) {
            throw new BusinessException("查询汇率的code 查询不到对应的系统参数信息！");
        }
        PayFindExchangeCfg findExchangeCfg = payFindExchangeCfgService.getById(req.getExchangeKeyId());
        if (findExchangeCfg == null) {
            throw new BusinessException("更新的信息不存在！");
        }
        Date date = new Date();
        BeanUtils.copyProperties(req, findExchangeCfg);
        findExchangeCfg.setExchangeUrl(parameterByCodeVO.getParamValue());
        if (StringUtils.isNotEmpty(parameterByCodeVO.getRemark()) && StringInnerUtils.isNumeric(parameterByCodeVO.getRemark())) {
            findExchangeCfg.setFrequency(Integer.valueOf(parameterByCodeVO.getRemark()));
        }
        findExchangeCfg.setUpdateTime(date);
        findExchangeCfg.setUpdateUser(loginAdmin.getAccLogin());
        payFindExchangeCfgService.updateById(findExchangeCfg);
    }

    public void delete(List<Long> ids, LoginUser loginAdmin) {
        for (Long id : ids) {
            PayFindExchangeCfg findExchangeCfg = payFindExchangeCfgService.getById(id);
            if (findExchangeCfg != null) {
                findExchangeCfg.setIsDelete(true);
                findExchangeCfg.setUpdateUser(loginAdmin.getAccLogin());
                findExchangeCfg.setUpdateTime(new Date());
                payFindExchangeCfgService.updateById(findExchangeCfg);
            }
        }

    }


    public List<QueryExchangeVO> exchangeDataSourceList() {
        //查询 获取汇率code参数信息
        List<QueryExchangeVO> voList = new ArrayList<>();
        List<SysParameterListVO> list = sysParameterService.getListByType("QUERY_EXCHANGE");
        voList = BeanCopyUtil.copyCollection(list, QueryExchangeVO.class);
        return voList;
    }
}
