package com.onelive.anchor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.anchor.service.MemBankAccountService;
import com.onelive.common.model.vo.mem.MemBankAccountVO;
import com.onelive.common.mybatis.entity.MemBankAccount;
import com.onelive.common.mybatis.mapper.master.mem.MemBankAccountMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemBankAccountMapper;

/**
 * <p>
 * 会员银行卡列表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-06
 */
@Service
public class MemBankAccountServiceImpl extends ServiceImpl<MemBankAccountMapper, MemBankAccount> implements MemBankAccountService {

    @Resource
    private SlaveMemBankAccountMapper slaveMemBankAccountMapper;
    @Resource
    private MemBankAccountMapper memBankAccountMapper;


    @Override
    public List<MemBankAccountVO> list(String account) {
        return slaveMemBankAccountMapper.list(account);
    }

    @Override
    public void add(MemBankAccount memBankAccount) {
        this.baseMapper.insert(memBankAccount);
    }

    @Override
    public Integer getBankCountByAccount(String account) {
        return slaveMemBankAccountMapper.getBankCountByAccount(account);
    }

    @Override
    public void updateMemBankInfo(MemBankAccount memBankAccount) {
        this.baseMapper.updateById(memBankAccount);
    }

    @Override
    public Integer getCountByAccountNo(String bankAccountNo) {
        QueryWrapper<MemBankAccount> wrapper = new QueryWrapper();
        wrapper.lambda().eq(MemBankAccount::getBankAccountNo, bankAccountNo).eq(MemBankAccount::getIsDelete, false);
        return slaveMemBankAccountMapper.selectCount(wrapper);
    }

    @Override
    public Integer getCountByAccountNoBankAccid(String bankAccountNo, Integer bankAccid) {
        QueryWrapper<MemBankAccount> wrapper = new QueryWrapper();
        wrapper.lambda().eq(MemBankAccount::getBankAccountNo, bankAccountNo)
                .eq(MemBankAccount::getIsDelete, false)
                .ne(MemBankAccount::getBankAccid, bankAccid);
        return slaveMemBankAccountMapper.selectCount(wrapper);
    }

    @Override
    public MemBankAccount getFirstBankName(String account) {
        QueryWrapper<MemBankAccount> wrapper = new QueryWrapper();
        wrapper.lambda().eq(MemBankAccount::getAccount, account)
                .eq(MemBankAccount::getIsDelete, false)
                .orderByAsc(MemBankAccount::getCreateTime)
                .select(MemBankAccount::getBankAccountName, MemBankAccount::getBankAccid).last("limit 1 ");
        //拿出第一条
        return slaveMemBankAccountMapper.selectOne(wrapper);
    }

    @Override
    public MemBankAccount getByAccountAndBankAccid(String account, Long bankAccid) {
        QueryWrapper<MemBankAccount> wrapper = new QueryWrapper();
        wrapper.lambda().eq(MemBankAccount::getAccount, account).eq(MemBankAccount::getBankAccid, bankAccid)
                .eq(MemBankAccount::getIsDelete, false).last("limit 1 ");
        return slaveMemBankAccountMapper.selectOne(wrapper);
    }

    @Override
    public void delete(Long bankAccid, String account) {
        memBankAccountMapper.deleteByBankAccidAndAccount(bankAccid, account, true);
    }

    @Override
    public void updateBankIsDefaultByAccount(String account, boolean isDefault) {
        UpdateWrapper<MemBankAccount> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda().set(MemBankAccount::getIsDefault, isDefault).eq(MemBankAccount::getAccount, account);
        this.update(updateWrapper);
    }

    @Override
    public void updateBankIsDefaultByAccountAndBankId(Long bankAccid, boolean isDefault) {
        UpdateWrapper<MemBankAccount> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda().set(MemBankAccount::getIsDefault, isDefault).eq(MemBankAccount::getBankAccid, bankAccid);
        this.update(updateWrapper);
    }

    @Override
    public MemBankAccount getAccount(String account) {
        QueryWrapper<MemBankAccount> wrapper = new QueryWrapper();
        wrapper.lambda().eq(MemBankAccount::getAccount, account).eq(MemBankAccount::getIsDefault,true)
                .eq(MemBankAccount::getIsDelete, false).last("limit 1 ");
        return slaveMemBankAccountMapper.selectOne(wrapper);
    }
}
