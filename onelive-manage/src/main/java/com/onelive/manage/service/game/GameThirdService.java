package com.onelive.manage.service.game;

import com.onelive.common.mybatis.entity.GameCategory;
import com.onelive.common.mybatis.entity.GameThird;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 第三方游戏 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-27
 */
public interface GameThirdService extends IService<GameThird> {
    /**
     * 根据分类状态更新游戏状态
     * @param category
     */
    void updateStatusByCategory(GameCategory category);

    /**
     * 根据gameCode查询游戏
     * @param gameCode
     * @return
     */
    GameThird getByGameCode(String gameCode);
}
