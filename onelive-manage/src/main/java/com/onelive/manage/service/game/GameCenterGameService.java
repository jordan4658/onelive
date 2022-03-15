package com.onelive.manage.service.game;

import com.onelive.common.mybatis.entity.GameCenterGame;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.GameThird;

/**
 * <p>
 * 彩票-国家对应表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-27
 */
public interface GameCenterGameService extends IService<GameCenterGame> {

    /**
     * 根据第三方游戏状态更新游戏中心游戏状态
     * @param third
     */
    void updateStatusByGameThird(GameThird third);
}
