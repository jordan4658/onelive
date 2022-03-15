package com.onelive.common.service.accountChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.MemUser;

/**
 * <p>
 * 基础用户表 服务类
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-01
 */
public interface MemInfoService extends IService<MemUser> {


    /**
     * 通过账号获取用户信息
     *
     * @param account
     * @return
     */
    MemUser queryByAccount(String account);


}
