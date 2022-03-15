package com.onelive.manage.service.game;

import com.onelive.common.mybatis.entity.GameType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 游戏类型表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-29
 */
public interface GameTypeService extends IService<GameType> {

	 /**
     * 分页查询
     */
    PageInfo<GameType> getList(Long countryId, Integer pageNum, Integer pageSize);
    
    public void deleteType(Long id, String account);
}
