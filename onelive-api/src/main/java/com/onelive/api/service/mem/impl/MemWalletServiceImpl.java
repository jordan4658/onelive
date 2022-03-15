package com.onelive.api.service.mem.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.mem.MemWalletService;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemWallet;
import com.onelive.common.mybatis.mapper.master.mem.MemWalletMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-13
 */
@Service
public class MemWalletServiceImpl extends ServiceImpl<MemWalletMapper, MemWallet> implements MemWalletService {

    /**
     * 查询平台钱包
     * @param userId
     * @return
     */
    @Override
    public MemWallet getWalletByMemId(Long userId,Integer walletType) {
        QueryWrapper<MemWallet> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemWallet::getUserId,userId).eq(MemWallet::getWalletType, walletType);
//        queryWrapper.lambda().eq(MemWallet::getMerchantCode, LoginInfoUtil.getMerchantCode());
        MemWallet wallet = this.baseMapper.selectOne(queryWrapper);
        return wallet;
    }

    /**
     * 初始化游戏钱包
     * @param user
     */
    @Override
    public void initGameWallet(MemUser user, Integer walletType) {
        WalletTypeEnum walletTypeEnum = WalletTypeEnum.getByType(walletType);
        if(walletTypeEnum==null){
            throw new BusinessException(StatusCode.GAME_WALLET_INIT_FAILURE);
        }
        MemWallet wallet = new MemWallet();
        wallet.setAccount(user.getUserAccount());
        wallet.setCreateUser(wallet.getAccount());
        wallet.setWalletType(walletType);
        wallet.setUserId(user.getId());
        this.save(wallet);
    }

    @Override
    public MemWallet getUserWalletByUserAccount(String userAccount, Integer gameWallet) {
        QueryWrapper<MemWallet> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemWallet::getAccount,userAccount).eq(MemWallet::getWalletType, gameWallet);
        MemWallet wallet = this.baseMapper.selectOne(queryWrapper);
        return wallet;
    }

    @Override
    public List<MemWallet> getWalletListByMemId(String userAccount) {
        QueryWrapper<MemWallet> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemWallet::getAccount,userAccount);
        List<MemWallet> wallets = this.baseMapper.selectList(queryWrapper);
        return wallets;
    }
}
