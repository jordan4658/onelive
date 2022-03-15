package com.onelive.api.service.mem.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.mem.MemWalletLangService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.mybatis.entity.MemWalletLang;
import com.onelive.common.mybatis.mapper.master.mem.MemWalletLangMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemWalletLangMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户第三方游戏钱包名称多语言表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-27
 */
@Service
public class MemWalletLangServiceImpl extends ServiceImpl<MemWalletLangMapper, MemWalletLang> implements MemWalletLangService {

    @Resource
    private SlaveMemWalletLangMapper slaveMemWalletLangMapper;

    @Override
    public List<MemWalletLang> getWalletLangList() {
        List<MemWalletLang> list = ApiBusinessRedisUtils.get(RedisKeys.MEM_WALLET_NAME_LANG);
        if (CollectionUtil.isNotEmpty(list)) {
            return list;
        }
        List<MemWalletLang> allList = slaveMemWalletLangMapper.selectList(null);
        ApiBusinessRedisUtils.set(RedisKeys.MEM_WALLET_NAME_LANG, list);
        return allList;
    }
}
