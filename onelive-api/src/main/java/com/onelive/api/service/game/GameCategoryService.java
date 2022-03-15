package com.onelive.api.service.game;

import com.onelive.common.mybatis.entity.GameCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-23
 */
public interface GameCategoryService extends IService<GameCategory> {

    /**
     * 根据分类ID查询数据
     * @param categoryId
     * @return
     */
    GameCategory getByCategoryId(Integer categoryId);
}
