package com.onelive.api.service.game;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.req.game.GameListByTagReq;
import com.onelive.common.mybatis.entity.GameCenterGame;

import java.util.List;

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
     * 根据标签和当前语言查询游戏
     * @param req
     * @return
     */
    List<GameCenterGame> listByTagWithLang(GameListByTagReq req);

    /**
     * 根据当前语言查询所有的游戏
     * @return
     */
    List<GameCenterGame> listWithCurrentLang();
}
