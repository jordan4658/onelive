package com.onelive.pay.model.business;

import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.req.mem.MemAccountBankDeleteReq;
import com.onelive.common.model.req.mem.MemBankAccountAddReq;
import com.onelive.common.model.req.mem.MemBankAccountUpdateReq;
import com.onelive.common.model.vo.mem.MemBankAccountVO;
import com.onelive.common.mybatis.entity.MemBankAccount;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.pay.service.MemBankAccountService;
import com.onelive.pay.service.SysBusParameterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: MemBankAccountBysiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/6 11:29
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemBankAccountBusiness {

    @Resource
    private MemBankAccountService memBankAccountService;
    @Resource
    private SysBusParameterService sysBusParameterService;

    /**
     * 查询会员银行卡列表-未分页
     *
     * @param user
     * @return
     */
    public List<MemBankAccountVO> list(AppLoginUser user) {
        List<MemBankAccountVO> list = memBankAccountService.list(user.getUserAccount());
        if (CollectionUtils.isNotEmpty(list)) {
            Iterator<MemBankAccountVO> iterator = list.iterator();
            while (iterator.hasNext()) {
                MemBankAccountVO vo = iterator.next();
                String accountNo = vo.getBankAccountNo();
                if (StringUtils.isNotBlank(accountNo) && accountNo.length() > 4) {
                    vo.setBankAccountNo(bankConvertToHidden(accountNo));
                }
                SysBusParameter param = sysBusParameterService.getByCode(vo.getBankCode());
                if (param == null) {
                    continue;
                }
                String bankName = param.getParamValue();
                String remark = param.getRemark();
                vo.setBankName(bankName);
                vo.setBankLogo(remark);
            }
        }
        return list;
    }

    /**
     * 会员新增-银行卡信息
     *
     * @param memBankAccountAddReq
     * @param user
     */
    @Transactional
    public void add(MemBankAccountAddReq memBankAccountAddReq, AppLoginUser user) throws Exception {

        if (StringUtils.isBlank(memBankAccountAddReq.getBankAccountNo())) {
            throw new BusinessException("银行卡号为空！");
        }
        String accountNo = memBankAccountAddReq.getBankAccountNo().replaceAll(" ", "");
        if (accountNo.length() < 16 || accountNo.length() > 20) {
            throw new BusinessException("银行卡号位数不对");
        }
        if (StringUtils.isBlank(memBankAccountAddReq.getBankAccountName())) {
            throw new BusinessException("开户行姓名为空！");
        }
        if (StringUtils.isBlank(memBankAccountAddReq.getBankCode())) {
            throw new BusinessException("所属银行为空！");
        }
        if (StringUtils.isBlank(memBankAccountAddReq.getBankAddress())) {
            throw new BusinessException("银行卡开户行地址为空！");
        }
//        if (StringUtils.isBlank(memBankAccountAddReq.getMobilePhone())) {
//            throw new BusinessException(StatusCode.PHONE_EMPTY);
//        }
//        if (StringUtils.isBlank(memBankAccountAddReq.getSmsCode())) {
//            throw new BusinessException(StatusCode.PHONECODE_EMPTY);
//        }
        SysBusParameter param = sysBusParameterService.getByCode(memBankAccountAddReq.getBankCode());
        if (param == null) {
            throw new BusinessException("所属银行错误！");
        }

        //检查手机验证码是否确定
//        sysShortMsgService.checkSmsCode(memBankAccountAddReq.getMobilePhone(), memBankAccountAddReq.getSmsCode(), SendTypeEnum.BINDBANK);

        //检查新增的银行卡号是否已经添加过的卡号
        Integer count = memBankAccountService.getCountByAccountNo(accountNo);
        if (count != 0) {
            throw new BusinessException("新增银行卡号已存在，请勿重复新增！");
        }
        //检查银行是否超过3张了
        Integer bankCount = memBankAccountService.getBankCountByAccount(user.getUserAccount());
        if (bankCount >= 5) {
            throw new BusinessException("您最多只能绑定5张银行卡！");
        }
//        //如果是第一张添加的，则更新到用户信息表的真实姓名字段
//        if (bankCount == 0) {
//            UserInfoUpdateDTO dto = new UserInfoUpdateDTO();
//            dto.setRealName(memBankAccountAddReq.getBankAccountName());
//            dto.setUid(user.getId());
//            memUserService.updateUserInfo(dto);
//        }
        //新增会员银行卡记录
        MemBankAccount memBankAccount = new MemBankAccount();
        BeanUtils.copyProperties(memBankAccountAddReq, memBankAccount);
        Date date = new Date();
        memBankAccount.setBankAccountNo(accountNo);
        memBankAccount.setAccount(user.getUserAccount());
        memBankAccount.setUpdateUser(user.getUserAccount());
        memBankAccount.setCreateTime(date);
        memBankAccount.setUpdateTime(date);
        memBankAccount.setIsDelete(false);
        memBankAccount.setCreateUser(user.getUserAccount());
        memBankAccountService.add(memBankAccount);
    }

    public void update(MemBankAccountUpdateReq memBankAccountUpdateReq, AppLoginUser user) throws Exception {

        if (memBankAccountUpdateReq.getBankAccid() == null) {
            throw new BusinessException("会员银行卡id为空！");
        }
        if (StringUtils.isBlank(memBankAccountUpdateReq.getBankAccountNo())) {
            throw new BusinessException("银行卡号为空！");
        }
        String accountNo = memBankAccountUpdateReq.getBankAccountNo().replaceAll(" ", "");
        if (accountNo.length() < 16 || accountNo.length() > 20) {
            throw new BusinessException("银行卡号位数不对");
        }
        if (StringUtils.isBlank(memBankAccountUpdateReq.getBankAccountName())) {
            throw new BusinessException("开户行姓名为空！");
        }
        if (StringUtils.isBlank(memBankAccountUpdateReq.getBankCode())) {
            throw new BusinessException("所属银行为空！");
        }
        if (StringUtils.isBlank(memBankAccountUpdateReq.getBankAddress())) {
            throw new BusinessException("银行卡开户行地址为空！");
        }
//        if (StringUtils.isBlank(memBankAccountUpdateReq.getMobilePhone())) {
//            throw new BusinessException(StatusCode.PHONE_EMPTY);
//        }
//        if (StringUtils.isBlank(memBankAccountUpdateReq.getSmsCode())) {
//            throw new BusinessException(StatusCode.PHONECODE_EMPTY);
//        }
//        //检查手机验证码是否确定
//        sysShortMsgService.checkSmsCode(memBankAccountUpdateReq.getMobilePhone(), memBankAccountUpdateReq.getSmsCode(), SendTypeEnum.BINDBANK);
        MemBankAccount memBankAccount = memBankAccountService.getById(memBankAccountUpdateReq.getBankAccid());
        if (memBankAccount == null) {
            throw new BusinessException("该会员的银行卡不存在！");
        }
        //检查更新的的银行卡号是否已经添加过的卡号
        Integer count = memBankAccountService.getCountByAccountNoBankAccid(accountNo, memBankAccountUpdateReq.getBankAccid());
        if (count != 0) {
            throw new BusinessException("更新的银行卡号已经存在，请重新填写新的卡号！");
        }
        BeanUtils.copyProperties(memBankAccountUpdateReq, memBankAccount);
        Date date = new Date();
        memBankAccount.setBankAccountNo(accountNo);
        memBankAccount.setAccount(user.getUserAccount());
        memBankAccount.setUpdateUser(user.getUserAccount());
        memBankAccount.setUpdateTime(date);
        memBankAccountService.updateMemBankInfo(memBankAccount);
//        //更新用户真实姓名
//        MemBankAccount firstAccount = memBankAccountService.getFirstBankName(user.getAccount());
//        if (memBankAccountUpdateReq.getBankAccid() == firstAccount.getBankAccid()) {
//            UserInfoUpdateDTO dto = new UserInfoUpdateDTO();
//            dto.setRealName(memBankAccount.getBankAccountName());
//            dto.setUid(user.getUid());
//            memUserService.updateUserInfo(dto);
//        }
    }

    public void delete(MemAccountBankDeleteReq memAccountBankDeleteReq, AppLoginUser user) {
        if (memAccountBankDeleteReq.getBankAccid() == null) {
            throw new BusinessException("银行卡id为空！");
        }
        memBankAccountService.delete(memAccountBankDeleteReq.getBankAccid(), user.getUserAccount());
    }

    public MemBankAccount getByAccountAndBankAccid(String account, Long bankAccid) {
        if (StringUtils.isBlank(account)) {
            throw new BusinessException("用户账号为空！");
        }
        if (bankAccid == null) {
            throw new BusinessException("银行卡id为空！");
        }
        return memBankAccountService.getByAccountAndBankAccid(account, bankAccid);
    }

    /**
     * 银行卡号进行隐式显示 例如 ：6214830119460961 ->  **** **** **** 0961
     *
     * @param bankNo
     * @return
     */
    private String bankConvertToHidden(String bankNo) {
        //也可以用正则表达式 String mask = number.replaceAll("\\w(?=\\w{4})", "*");
        StringBuffer hiddenBankNo = new StringBuffer();
        String cutOutBankNo = bankNo.substring(bankNo.length() - 4, bankNo.length());
        hiddenBankNo.append(PayConstants.CONVERT_STR).append(cutOutBankNo);
        return hiddenBankNo.toString();
    }

    /**
     * 设置用的默认银行卡
     *
     * @param account
     * @param bankAccid
     * @return
     */
    @Transactional
    public void setAccountIsDefaultBank(String account, Long bankAccid) {
        if (StringUtils.isBlank(account)) {
            throw new BusinessException("用户账号为空！");
        }
        if (bankAccid == null) {
            throw new BusinessException("银行卡id为空！");
        }
        MemBankAccount memBankAccount = memBankAccountService.getAccount(account);
        if (memBankAccount == null) {
            memBankAccountService.updateBankIsDefaultByAccountAndBankId(bankAccid, true);
            return;
        }
        memBankAccountService.updateBankIsDefaultByAccountAndBankId(memBankAccount.getBankAccid(), true);
        memBankAccountService.updateBankIsDefaultByAccountAndBankId(bankAccid, true);
    }
}
