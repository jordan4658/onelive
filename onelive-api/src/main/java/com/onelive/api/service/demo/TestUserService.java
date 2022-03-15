package com.onelive.api.service.demo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.TestUser;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-03-31
 */
public interface TestUserService extends IService<TestUser> {

    PageInfo<TestUser> getSlave(Integer pageNum, Integer pageSize);

    void insert_master(TestUser user);

    void insert_slave(TestUser user);
}
