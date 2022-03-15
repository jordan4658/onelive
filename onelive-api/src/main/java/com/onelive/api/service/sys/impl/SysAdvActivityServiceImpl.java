package com.onelive.api.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.sys.SysAdvActivityService;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.model.req.mem.usercenter.ActivityListDTO;
import com.onelive.common.mybatis.entity.SysAdvActivity;
import com.onelive.common.mybatis.mapper.master.sys.SysAdvActivityMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysAdvActivityMapper;
import com.onelive.common.utils.others.CollectionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 广告首页轮播表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-17
 */
@Service
public class SysAdvActivityServiceImpl extends ServiceImpl<SysAdvActivityMapper, SysAdvActivity> implements SysAdvActivityService {

    @Resource
    private SlaveSysAdvActivityMapper slaveSysAdvActivityMapper;


    @Override
    public List<SysAdvActivity> listWithCountryAndLang(ActivityListDTO dto) {
        return slaveSysAdvActivityMapper.listWithCountryAndLang(dto);
    }
}
