package com.onelive.manage.service.game.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.GameCategory;
import com.onelive.common.mybatis.entity.GameThird;
import com.onelive.common.mybatis.mapper.master.game.GameThirdMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameThirdMapper;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.manage.service.game.GameCenterGameService;
import com.onelive.manage.service.game.GameThirdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    private GameCenterGameService gameCenterGameService;

    @Resource
    private SlaveGameThirdMapper slaveGameThirdMapper;

    @Override
    public void updateStatusByCategory(GameCategory category) {
        if(category==null){
            return;
        }
        //查询所有的游戏
        QueryWrapper<GameThird> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameThird::getCategoryId,category.getCategoryId()).eq(GameThird::getPlatformCode,category.getPlatformCode()).eq(GameThird::getIsDelete,false);
        List<GameThird> list = slaveGameThirdMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            Integer status = category.getStatus();
            for(GameThird game:list){
                if(game.getStatus().equals(status)){
                    continue;
                }
                game.setStatus(status);
                this.updateById(game);
                if(game.getIsWork()) {
                    //更新游戏中心的游戏状态
                    gameCenterGameService.updateStatusByGameThird(game);
                }
            }
        }
    }

    @Override
    public GameThird getByGameCode(String gameCode) {
        QueryWrapper<GameThird> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(GameThird::getGameCode,gameCode).eq(GameThird::getIsDelete,false).last(" LIMIT 1");
        return slaveGameThirdMapper.selectOne(queryWrapper);
    }
}
