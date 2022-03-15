package com.onelive.common.mybatis.mapper.master.demo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.mybatis.entity.TestUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-03-31
 */
@Mapper
public interface TestUsersMapper extends BaseMapper<TestUser> {


    List<TestUser> getSlave();


    void insert_master(TestUser user);

    void insert_master2(TestUser user);
}
