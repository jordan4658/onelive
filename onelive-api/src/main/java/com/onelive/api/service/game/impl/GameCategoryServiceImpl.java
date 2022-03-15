package com.onelive.api.service.game.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.game.GameCategoryService;
import com.onelive.common.mybatis.entity.GameCategory;
import com.onelive.common.mybatis.mapper.master.game.GameCategoryMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-22
 */
@Service
public class GameCategoryServiceImpl extends ServiceImpl<GameCategoryMapper, GameCategory> implements GameCategoryService {

    @Resource
    private SlaveGameCategoryMapper slaveGameCategoryMapper;

    @Override
    public GameCategory getByCategoryId(Integer categoryId) {
        QueryWrapper<GameCategory> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.lambda().eq(GameCategory::getCategoryId, categoryId).last(" LIMIT 1");
        return slaveGameCategoryMapper.selectOne(categoryWrapper);
    }
}
