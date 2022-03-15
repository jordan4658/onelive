package com.onelive.api.service.game;

import com.onelive.common.mybatis.entity.GameThird;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 第三方游戏 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-23
 */
public interface GameThirdService extends IService<GameThird> {

    GameThird getByCode(String code);

}
