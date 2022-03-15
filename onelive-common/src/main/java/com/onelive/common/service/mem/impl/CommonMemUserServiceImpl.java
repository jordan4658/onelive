package com.onelive.common.service.mem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.mapper.master.mem.MemUserMapper;
import com.onelive.common.service.mem.CommonMemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CommonMemUserServiceImpl  extends ServiceImpl<MemUserMapper, MemUser>   implements CommonMemUserService {

}
