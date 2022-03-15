package com.onelive.manage.service.game.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.GameTag;
import com.onelive.common.mybatis.mapper.master.game.GameTagMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameTagMapper;
import com.onelive.manage.service.game.GameTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 游戏分类标签, 用于前端显示 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-27
 */
@Service
public class GameTagServiceImpl extends ServiceImpl<GameTagMapper, GameTag> implements GameTagService {

    @Resource
    private SlaveGameTagMapper slaveGameTagMapper;

    @Override
    public GameTag getByCode(String code) {
        QueryWrapper<GameTag> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameTag::getIsDelete,false).eq(GameTag::getCode,code).last(" LIMIT 1");
        return slaveGameTagMapper.selectOne(queryWrapper);
    }
}
