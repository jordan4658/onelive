package com.onelive.manage.service.game;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.GameCategory;
import com.onelive.common.mybatis.entity.GameThirdPlatform;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-23
 */
public interface GameCategoryService extends IService<GameCategory> {

	List<GameCategory> getList();

    /**
     * 根据分类ID查询分类
     * @param categoryId
     * @return
     */
    GameCategory getByCategoryId(Integer categoryId);

    /**
     * 根据平台的状态更新分类的状态
     * @param platform
     */
    void updateStatusByPlatform(GameThirdPlatform platform);
}
