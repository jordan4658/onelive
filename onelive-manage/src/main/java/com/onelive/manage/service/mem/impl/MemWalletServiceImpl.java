package com.onelive.manage.service.mem.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.mybatis.entity.MemWallet;
import com.onelive.common.mybatis.mapper.master.mem.MemWalletMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemWalletMapper;
import com.onelive.manage.service.mem.MemWalletService;
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
public class MemWalletServiceImpl extends ServiceImpl<MemWalletMapper, MemWallet> implements MemWalletService {

    @Resource
    private SlaveMemWalletMapper slaveMemWalletMapper;

    @Override
    public SlaveMemWalletMapper getSalveMapper() {
        return this.slaveMemWalletMapper;
    }

    @Override
    public MemWallet getMemWalletByAccount(String account, Integer walletType) {
        QueryWrapper<MemWallet> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemWallet::getAccount, account);
        queryWrapper.lambda().eq(MemWallet::getWalletType, walletType);
        queryWrapper.lambda().eq(MemWallet::getIsDelete, false).last("limit 1 ");
        MemWallet wallet = slaveMemWalletMapper.selectOne(queryWrapper);
        if (wallet == null) {
            wallet = new MemWallet();
            wallet.setAccount(account);
            wallet.setCreateUser(wallet.getAccount());
            wallet.setWalletType(WalletTypeEnum.WOODEN_PLATFORM.getCode());
            this.save(wallet);
        }
        return wallet;
    }

    @Override
    public MemWallet getByAccount(String account, Integer walletType) {
        QueryWrapper<MemWallet> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(MemWallet::getAccount, account).eq(MemWallet::getWalletType, walletType);
        queryWrapper.lambda().eq(MemWallet::getIsDelete, false).last("limit 1 ");
        return slaveMemWalletMapper.selectOne(queryWrapper);
    }

}
