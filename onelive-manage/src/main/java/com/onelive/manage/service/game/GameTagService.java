package com.onelive.manage.service.game;

import com.onelive.common.mybatis.entity.GameTag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 游戏分类标签, 用于前端显示 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-27
 */
public interface GameTagService extends IService<GameTag> {

    GameTag getByCode(String code);
}
