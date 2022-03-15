package com.onelive.manage.service.game;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.GameModule;

/**
 * <p>
 * 游戏模块表 服务类
 * </p>
 */
public interface GameModuleService extends IService<GameModule> {

	 /**
     * 分页查询
     */
    PageInfo<GameModule> getList(Long typeId, Integer pageNum, Integer pageSize);
    
    public void deleteModule(Long id, String account);
}
