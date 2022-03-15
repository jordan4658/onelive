package com.onelive.manage.service.finance.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.finance.memlevel.MemLevelDTO;
import com.onelive.common.model.req.finance.memlevel.MemLevelListReq;
import com.onelive.common.mybatis.entity.MemLevel;
import com.onelive.common.mybatis.mapper.master.finance.MemLevelMapper;
import com.onelive.common.mybatis.mapper.slave.finance.SlaveMemLevelMapper;
import com.onelive.manage.converter.finance.MemLevelConverter;
import com.onelive.manage.service.finance.MemLevelService;

/**
 * <p>
 * 用户层级表 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2021-10-26
 */
@Service
public class MemLevelServiceImpl extends ServiceImpl<MemLevelMapper, MemLevel> implements MemLevelService {

    @Resource
    private MemLevelConverter memLevelConverter;
    @Resource
    private SlaveMemLevelMapper slaveMemLevelMapper;


    @Override
    public PageInfo<MemLevelDTO> getPage(final MemLevelListReq memLevelListReq) {
        MemLevel memLevelQuery = memLevelConverter.toE(memLevelListReq);
        PageHelper.startPage(memLevelListReq.getPageNum(), memLevelListReq.getPageSize());
        final List<MemLevel> memLevels = slaveMemLevelMapper.selectList(new QueryWrapper<>(memLevelQuery).orderByAsc("sort"));
        PageInfo<MemLevel> entityPageInfo = new PageInfo<>(memLevels);
        return memLevelConverter.toDTOPage(entityPageInfo);
    }

    @Override
    public MemLevelDTO getById(final Long id) {
        MemLevel memLevel = slaveMemLevelMapper.selectById(id);
        if (null == memLevel) {
            return null;
        }
        return memLevelConverter.toDTO(slaveMemLevelMapper.selectById(id));
    }
}
