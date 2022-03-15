package com.onelive.common.service.accountChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemWallet;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-12
 */
public interface WalletService extends IService<MemWallet> {

    MemWallet getByAccount(String account, Integer walletType);

    MemWallet getByWalletId(Long walletId);

}
