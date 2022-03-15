package com.onelive.manage.service.game;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.GameOutsideGamesRate;

/**
 * <p>
 * 第三方游戏汇率 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-29
 */
public interface GameOutsideGamesRateService extends IService<GameOutsideGamesRate> {

	 /**
     * 分页查询
     */
    PageInfo<GameOutsideGamesRate> getList(Long gameId, Integer pageNum, Integer pageSize);
    
}
