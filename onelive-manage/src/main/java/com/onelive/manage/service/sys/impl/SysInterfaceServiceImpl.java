package com.onelive.manage.service.sys.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.SysInterface;
import com.onelive.common.mybatis.mapper.master.sys.SysInterfaceMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysInterfaceMapper;
import com.onelive.manage.service.sys.SysInterfaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 后台系统接口信息 服务实现类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
@Service
public class SysInterfaceServiceImpl extends ServiceImpl<SysInterfaceMapper, SysInterface> implements SysInterfaceService {

    @Resource
    private SlaveSysInterfaceMapper slaveSysInterfaceMapper;

}
