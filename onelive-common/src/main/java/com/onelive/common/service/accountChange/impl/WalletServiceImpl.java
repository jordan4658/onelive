package com.onelive.common.service.accountChange.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.service.accountChange.WalletService;
import com.onelive.common.mybatis.entity.MemWallet;
import com.onelive.common.mybatis.mapper.master.mem.MemWalletMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemWalletMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-12
 */
@Service
public class WalletServiceImpl extends ServiceImpl<MemWalletMapper, MemWallet> implements WalletService {

    @Resource
    private SlaveMemWalletMapper slaveMemWalletMapper;

    @Override
    public MemWallet getByAccount(String account, Integer walletType) {
        QueryWrapper<MemWallet> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(MemWallet::getAccount, account).eq(MemWallet::getWalletType, walletType);
        queryWrapper.lambda().eq(MemWallet::getIsDelete, false).last("limit 1 ");
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public MemWallet getByWalletId(Long walletId) {
        QueryWrapper<MemWallet> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(MemWallet::getWalletId, walletId);
        queryWrapper.lambda().eq(MemWallet::getIsDelete, false).last("limit 1 ");
        return this.baseMapper.selectOne(queryWrapper);
    }
}
