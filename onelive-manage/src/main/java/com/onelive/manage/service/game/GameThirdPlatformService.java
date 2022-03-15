package com.onelive.manage.service.game;

import com.onelive.common.mybatis.entity.GameThirdPlatform;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 第三方游戏平台 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-20
 */
public interface GameThirdPlatformService extends IService<GameThirdPlatform> {

    /**
     * 根据平台代码查询平台信息
     * @param platformCode
     * @return
     */
    GameThirdPlatform getByCode(String platformCode);

}
