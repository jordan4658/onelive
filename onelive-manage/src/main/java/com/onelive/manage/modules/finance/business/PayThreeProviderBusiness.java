package com.onelive.manage.modules.finance.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.sys.SysBusParameterConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.pay.PayThreeProviderAddBackReq;
import com.onelive.common.model.req.pay.PayThreeProviderUpBackReq;
import com.onelive.common.model.req.pay.PayThreeProviderUpStatusBackReq;
import com.onelive.common.model.vo.pay.PayThreeProviderBackVO;
import com.onelive.common.model.vo.pay.PayThreeProviderSelectVO;
import com.onelive.common.mybatis.entity.PayThreeProvider;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.manage.service.finance.PayThreeProviderService;
import com.onelive.manage.service.sys.SysBusParameterService;
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
 * @ClassName: PayThreeProviderBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/17 12:09
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PayThreeProviderBusiness {

    @Resource
    private PayThreeProviderService payThreeProviderService;

    @Resource
    private SysBusParameterService sysBusParameterService;


    /**
     * 支付商参数校验
     *
     * @param payThreeProvider
     */
    private void parameterVerify(PayThreeProvider payThreeProvider) {

        if (payThreeProvider.getProviderType() == null) {
            throw new BusinessException("支付商类型为空！");
        }
        if (StringUtils.isBlank(payThreeProvider.getProviderCode())) {
            throw new BusinessException("平台设置的-商戶code为空！");
        }
        if (PayConstants.PayProviderTypeEnum.OFFLINE.getCode() == payThreeProvider.getProviderType()) {
            //线下支付商 参数校验
            if (StringUtils.isBlank(payThreeProvider.getBankAccountNo())) {
                throw new BusinessException("银行卡号为空！");
            }
            if (StringUtils.isBlank(payThreeProvider.getBankName())) {
                throw new BusinessException("银行名称为空！");
            }
            if (StringUtils.isBlank(payThreeProvider.getBankAccountName())) {
                throw new BusinessException("开户名为空！");
            }
            if (StringUtils.isBlank(payThreeProvider.getBankAddress())) {
                throw new BusinessException("开户行地址为空！");
            }
        } else if (PayConstants.PayProviderTypeEnum.ONLINE.getCode() == payThreeProvider.getProviderType()) {
            //线上支付商 参数校验
            if (StringUtils.isBlank(payThreeProvider.getBackUrl())) {
                throw new BusinessException("第三方回调接口地址为空！");
            }
            if (StringUtils.isBlank(payThreeProvider.getAddOrderUrl())) {
                throw new BusinessException("第三方支付下单url地址为空！");
            }
            if (StringUtils.isBlank(payThreeProvider.getAgentNo())) {
                throw new BusinessException("第三方商户号为空！");
            }
            if (StringUtils.isBlank(payThreeProvider.getAllowIps())) {
                throw new BusinessException("回调白名单为空！");
            }
            if (StringUtils.isBlank(payThreeProvider.getGetOrderUrl())) {
                throw new BusinessException("三方支付订单查询url为空！");
            }
            if (StringUtils.isBlank(payThreeProvider.getPriSecret())) {
                throw new BusinessException("商戶私钥为空！");
            }
            if (StringUtils.isBlank(payThreeProvider.getPubSecret())) {
                throw new BusinessException("商戶公钥为空！");
            }
            if (StringUtils.isBlank(payThreeProvider.getSecretCode())) {
                throw new BusinessException("商戶密钥为空！");
            }
        } else {
            throw new BusinessException("支付商类型错误！");
        }
    }

    /**
     * 查询-支付商列表 分页
     *
     * @param user
     * @param pageNum
     * @param pageSize
     * @param providerName
     * @param providerType
     * @return
     */
    public PageInfo<PayThreeProviderBackVO> listPage(LoginUser user, Integer pageNum, Integer pageSize, String providerName, Integer providerType) {

        if (providerType != null) {
            Boolean flag = true;
            for (PayConstants.PayProviderTypeEnum providerTypeEnum : PayConstants.PayProviderTypeEnum.values()) {
                if (providerTypeEnum.getCode() == providerType) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                throw new BusinessException("支付商类型错误！");
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<PayThreeProviderBackVO> list = payThreeProviderService.listPage(providerName, providerType);
        return new PageInfo<PayThreeProviderBackVO>(list);
    }

    /**
     * 新增支付商
     *
     * @param payThreeProviderAddBackReq
     * @param user
     */
    public void add(PayThreeProviderAddBackReq payThreeProviderAddBackReq, LoginUser user) {
        if (payThreeProviderAddBackReq == null) {
            throw new BusinessException("支付商信息为空！");
        }
        PayThreeProvider payThreeProvider = new PayThreeProvider();
        Date date = new Date();
        BeanUtils.copyProperties(payThreeProviderAddBackReq, payThreeProvider);
        parameterVerify(payThreeProvider);
        payThreeProvider.setCreateUser(user.getAccLogin());
        payThreeProvider.setUpdateUser(user.getAccLogin());
        payThreeProvider.setCreateTime(date);
        payThreeProvider.setUpdateTime(date);
        payThreeProviderService.save(payThreeProvider);
    }

    /**
     * 根据支付商id更新支付商信息
     *
     * @param payThreeProviderUpBackReq
     * @param user
     */
    public void update(PayThreeProviderUpBackReq payThreeProviderUpBackReq, LoginUser user) {
        if (payThreeProviderUpBackReq == null) {
            throw new BusinessException("支付商信息为空！");
        }
        PayThreeProvider payThreeProvider = new PayThreeProvider();
        Date date = new Date();
        BeanUtils.copyProperties(payThreeProviderUpBackReq, payThreeProvider);
        parameterVerify(payThreeProvider);
        payThreeProvider.setCreateUser(user.getAccLogin());
        payThreeProvider.setUpdateUser(user.getAccLogin());
        payThreeProvider.setCreateTime(date);
        payThreeProvider.setUpdateTime(date);
        payThreeProviderService.updateById(payThreeProvider);

    }

    /**
     * 根据支付商ID 禁用、启用  支付商
     *
     * @param payThreeProviderUpStatusBackReq
     * @param user
     */
    public void updateEnable(PayThreeProviderUpStatusBackReq payThreeProviderUpStatusBackReq, LoginUser user) {
        if (payThreeProviderUpStatusBackReq == null) {
            throw new BusinessException("支付商信息为空！");
        }
        if (payThreeProviderUpStatusBackReq.getProviderId() == null) {
            throw new BusinessException("支付商ID为空！");
        }
        if (payThreeProviderUpStatusBackReq.getStatus() == null) {
            throw new BusinessException("支付商状态为空！");
        }
        Boolean flag = true;
        for (PayConstants.StatusEnum statusEnum : PayConstants.StatusEnum.values()) {
            if (statusEnum.getCode() == payThreeProviderUpStatusBackReq.getStatus()) {
                flag = false;
                break;
            }
        }
        if (flag) {
            throw new BusinessException("支付商状态错误！");
        }
        PayThreeProvider payThreeProvider = new PayThreeProvider();
        Date date = new Date();
        payThreeProvider.setProviderId(payThreeProviderUpStatusBackReq.getProviderId());
        payThreeProvider.setStatus(payThreeProviderUpStatusBackReq.getStatus());
        payThreeProvider.setUpdateUser(user.getAccLogin());
        payThreeProvider.setUpdateTime(date);
        payThreeProviderService.updateById(payThreeProvider);
    }

    public List<SysBusParameter> bankList() {
        return sysBusParameterService.getChild(SysBusParameterConstants.BANK_LIST_CODE);
    }

    public List<PayThreeProviderSelectVO> selectList(Integer providerType) {

       return payThreeProviderService.selectList(providerType);
    }
}
