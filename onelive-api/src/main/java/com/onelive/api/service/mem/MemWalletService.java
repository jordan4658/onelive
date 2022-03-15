package com.onelive.api.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemWallet;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-13
 */
public interface MemWalletService extends IService<MemWallet> {

    MemWallet getWalletByMemId(Long userId, Integer gameWallet);

    /**
     * 初始化游戏钱包
     * @param user
     */
    void initGameWallet(MemUser user, Integer walletType);

    MemWallet getUserWalletByUserAccount(String userAccount,Integer gameWallet);


    List<MemWallet> getWalletListByMemId(String userAccount);
}
