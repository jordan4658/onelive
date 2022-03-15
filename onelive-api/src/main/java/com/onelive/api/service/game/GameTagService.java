package com.onelive.api.service.game;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.GameTag;

import java.util.List;

/**
 * <p>
 * 游戏分类标签, 用于前端显示 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-27
 */
public interface GameTagService extends IService<GameTag> {

    /**
     * 根据用户当前的国家code和lang查询标签列表
     * @return
     */
    List<GameTag> listWithCurrentLang();
}
