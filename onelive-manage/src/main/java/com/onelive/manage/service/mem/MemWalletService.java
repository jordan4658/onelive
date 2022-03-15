package com.onelive.manage.service.mem;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemWallet;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemWalletMapper;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-12
 */
public interface MemWalletService extends IService<MemWallet> {
    /**
     * 获取从的mapper
     *
     * @return
     */
    SlaveMemWalletMapper getSalveMapper();

    /**
     * 通过账号获取用户对应类型的钱包
     *
     * @param account
     * @param walletType
     * @return
     */
    MemWallet getMemWalletByAccount(String account, Integer walletType);

    MemWallet getByAccount(String account, Integer walletType);
}
