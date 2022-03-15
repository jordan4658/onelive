package com.onelive.manage.service.game.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.GameCenterGame;
import com.onelive.common.mybatis.entity.GameThird;
import com.onelive.common.mybatis.mapper.master.game.GameCenterGameMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameCenterGameMapper;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.manage.service.game.GameCenterGameService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 彩票-国家对应表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-27
 */
@Service
public class GameCenterGameServiceImpl extends ServiceImpl<GameCenterGameMapper, GameCenterGame> implements GameCenterGameService {

    @Resource
    private SlaveGameCenterGameMapper slaveGameCenterGameMapper;

    @Override
    public void updateStatusByGameThird(GameThird third) {
        if(third == null){
            return;
        }
        QueryWrapper<GameCenterGame> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameCenterGame::getIsDelete,false).eq(GameCenterGame::getGameCode,third.getGameCode());
        List<GameCenterGame> gameList = slaveGameCenterGameMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(gameList)){
            Integer status = third.getStatus();
            for(GameCenterGame game:gameList){
                if(game.getStatus().equals(status)){
                    continue;
                }
                game.setStatus(status);
                this.updateById(game);
            }
        }
    }
}
