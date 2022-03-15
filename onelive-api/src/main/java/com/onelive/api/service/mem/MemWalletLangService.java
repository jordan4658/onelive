package com.onelive.api.service.mem;

import com.onelive.common.mybatis.entity.MemWalletLang;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户第三方游戏钱包名称多语言表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-27
 */
public interface MemWalletLangService extends IService<MemWalletLang> {

    List<MemWalletLang> getWalletLangList();
}
