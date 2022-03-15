package com.onelive.job.service.mem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.mapper.master.mem.MemUserMapper;
import com.onelive.job.service.mem.MemUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-17
 */
@Service
public class MemUserServiceImpl extends ServiceImpl<MemUserMapper, MemUser> implements MemUserService {

}
