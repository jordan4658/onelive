package com.onelive.manage.service.game;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.GameOutsideGames;

/**
 * <p>
 * 第三方游戏 服务类
 * </p>
 */
public interface GameOutsideGamesService extends IService<GameOutsideGames> {


	 /**
    * 分页查询
    */
   PageInfo<GameOutsideGames> getList(Integer pageNum, Integer pageSize);
   
}
