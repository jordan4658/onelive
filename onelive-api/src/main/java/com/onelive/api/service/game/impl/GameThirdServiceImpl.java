package com.onelive.api.service.game.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.game.GameThirdService;
import com.onelive.common.mybatis.entity.GameThird;
import com.onelive.common.mybatis.mapper.master.game.GameThirdMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameThirdMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 第三方游戏 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-23
 */
@Service
public class GameThirdServiceImpl extends ServiceImpl<GameThirdMapper, GameThird> implements GameThirdService {

    @Resource
    private SlaveGameThirdMapper slaveGameThirdMapper;

    @Override
    public GameThird getByCode(String code) {
        QueryWrapper<GameThird> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameThird::getIsDelete,false).eq(GameThird::getGameCode,code).last(" LIMIT 1");
        return slaveGameThirdMapper.selectOne(queryWrapper);
    }
}
