package com.onelive.manage.service.mem.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.vo.mem.MemGroupCurrencyCfgVO;
import com.onelive.common.mybatis.entity.MemGroupCurrencyCfg;
import com.onelive.common.mybatis.entity.MemUserGroup;
import com.onelive.common.mybatis.mapper.master.mem.MemGroupCurrencyCfgMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemGroupCurrencyCfgMapper;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.mem.MemGroupCurrencyCfgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户层级 出入款配置 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-16
 */
@Service
public class MemGroupCurrencyCfgServiceImpl extends ServiceImpl<MemGroupCurrencyCfgMapper, MemGroupCurrencyCfg> implements MemGroupCurrencyCfgService {


    @Resource
    private SlaveMemGroupCurrencyCfgMapper slaveMemGroupCurrencyCfgMapper;


    @Override
    public List<MemGroupCurrencyCfgVO> getByUserGroupIds(List<Long> groupIds) {
        QueryWrapper<MemGroupCurrencyCfg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MemGroupCurrencyCfg::getIsDelete, false);
        queryWrapper.lambda().in(MemGroupCurrencyCfg::getUserGroupId,groupIds);
        List<MemGroupCurrencyCfg> list=slaveMemGroupCurrencyCfgMapper.selectList(queryWrapper);
        return BeanCopyUtil.copyCollection(list,MemGroupCurrencyCfgVO.class);
    }
}
