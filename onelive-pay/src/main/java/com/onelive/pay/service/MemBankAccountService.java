package com.onelive.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.mem.MemBankAccountVO;
import com.onelive.common.mybatis.entity.MemBankAccount;

import java.util.List;

/**
 * <p>
 * 会员银行卡列表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-06
 */
public interface MemBankAccountService extends IService<MemBankAccount> {

    List<MemBankAccountVO> list(String account);

    void add(MemBankAccount memBankAccount);

    Integer getBankCountByAccount(String account);

    void updateMemBankInfo(MemBankAccount memBankAccount);

    Integer getCountByAccountNo(String bankAccountNo);

    Integer getCountByAccountNoBankAccid(String bankAccountNo, Integer bankAccid);

    /**
     * 获取用户的第一张银行卡的账号名称
     *
     * @param account
     * @return
     */
    MemBankAccount getFirstBankName(String account);

    MemBankAccount getByAccountAndBankAccid(String account, Long bankAccid);

    void delete(Long bankAccid, String account);

    void updateBankIsDefaultByAccount(String account, boolean isDefault);

    void updateBankIsDefaultByAccountAndBankId(Long bankAccid, boolean isDefault);

    MemBankAccount getAccount(String account);
}
