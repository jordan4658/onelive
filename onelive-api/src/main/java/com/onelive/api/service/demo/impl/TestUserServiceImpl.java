package com.onelive.api.service.demo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.demo.TestUserService;
import com.onelive.common.mybatis.entity.TestUser;
import com.onelive.common.mybatis.mapper.master.demo.TestUsersMapper;
import com.onelive.common.mybatis.mapper.slave.demo.SlaveTestUsersMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-03-31
 */
@Service
public class TestUserServiceImpl extends ServiceImpl<TestUsersMapper, TestUser> implements TestUserService {

    @Resource
    private SlaveTestUsersMapper slaveTestUsersMapper;

    @Override
    public PageInfo<TestUser> getSlave(Integer pageNum, Integer pageSize) {
//        QueryWrapper<TestUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(TestUser::getName, "113");
//
//        TestUser dd = slaveTestUsersMapper.selectOne(queryWrapper);

//        UpdateWrapper<TestUser> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.lambda().set(TestUser::getName, "223")
//                .eq(TestUser::getId, dd.getId());
//        this.baseMapper.update(null, updateWrapper);


        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> slaveTestUsersMapper.getSlave());
    }

    @Override
    public void insert_master(TestUser user) {
        user.setId(null);
        this.baseMapper.insert_master(user);
//        testUsersMapper.insert_master2(user);
//        throw new RuntimeException("测试事务回滚！");

    }

    @Override
    public void insert_slave(TestUser user) {
        user.setId(null);
        slaveTestUsersMapper.insert_master(user);
//        testUsersMapper.insert_master2(user);
//        throw new RuntimeException("测试事务回滚！");

    }
}
