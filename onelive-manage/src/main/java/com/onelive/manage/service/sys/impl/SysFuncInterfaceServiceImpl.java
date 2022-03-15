package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.SysFuncInterface;
import com.onelive.common.mybatis.mapper.master.sys.SysFuncInterfaceMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysFuncInterfaceMapper;
import com.onelive.manage.service.sys.SysFuncInterfaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 功能接口关系 服务实现类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
@Service
public class SysFuncInterfaceServiceImpl extends ServiceImpl<SysFuncInterfaceMapper, SysFuncInterface> implements SysFuncInterfaceService {

    @Resource
    private SlaveSysFuncInterfaceMapper slaveSysFuncInterfaceMapper;

    @Override
    public List<String> getInterfaceUrlsByRole(List<Long> param) {
        return slaveSysFuncInterfaceMapper.getInterfaceUrlsByRole(param);
    }

}
