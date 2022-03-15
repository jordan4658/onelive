package com.onelive.manage.service.mem.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.mem.MemUserGroupManageReq;
import com.onelive.common.model.vo.mem.MemUserGroupManageVO;
import com.onelive.common.model.vo.mem.MemUserGroupVO;
import com.onelive.common.mybatis.entity.MemUserGroup;
import com.onelive.common.mybatis.entity.PayExchangeCurrency;
import com.onelive.common.mybatis.mapper.master.mem.MemUserGroupMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserGroupMapper;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.mem.MemUserGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户层级表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-16
 */
@Service
public class MemUserGroupServiceImpl extends ServiceImpl<MemUserGroupMapper, MemUserGroup> implements MemUserGroupService {


    @Resource
    private SlaveMemUserGroupMapper slaveMemUserGroupMapper;

    @Override
    public PageInfo<MemUserGroupVO> pageList(Integer pageSize, Integer pageNum,String currencyCode) {
        PageHelper.startPage(pageNum, pageSize);
        List<MemUserGroupVO> list = slaveMemUserGroupMapper.pageList(currencyCode);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<MemUserGroupManageVO> getMemUserGroupManageList(MemUserGroupManageReq req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<MemUserGroupManageVO> list = slaveMemUserGroupMapper.getMemUserGroupManageList(req);
        return new PageInfo<>(list);
    }
}
