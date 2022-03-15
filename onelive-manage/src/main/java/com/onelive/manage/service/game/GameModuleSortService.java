package com.onelive.manage.service.game;

import com.onelive.common.mybatis.entity.GameModule;
import com.onelive.common.mybatis.entity.GameModuleSort;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 游戏排序 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-29
 */
public interface GameModuleSortService extends IService<GameModuleSort> {

	 /**
     * 分页查询
     */
    PageInfo<GameModuleSort> getList(Long moduleId, Integer pageNum, Integer pageSize);
    
    public void deleteModuleSort(String ids, String account);
}
