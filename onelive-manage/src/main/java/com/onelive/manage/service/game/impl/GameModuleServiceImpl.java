package com.onelive.manage.service.game.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.GameModule;
import com.onelive.common.mybatis.mapper.master.game.GameModuleMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.game.GameModuleService;

/**
 * <p>
 * 游戏模块表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-29
 */
@Service
public class GameModuleServiceImpl extends ServiceImpl<GameModuleMapper, GameModule> implements GameModuleService {

    @Resource
    private GameModuleMapper gameModuleMapper;
	/* （非 Javadoc）
	 * @see com.onelive.manage.service.game.GameModuleService#getList(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public PageInfo<GameModule> getList(Long typeId, Integer pageNum, Integer pageSize) {
	    LambdaQueryWrapper<GameModule> wrapper = Wrappers.<GameModule>lambdaQuery()
                .eq(typeId != null,  GameModule::getTypeId, typeId)
                .eq(GameModule::getMerchantCode, LoginInfoUtil.getMerchantCode());
	    PageInfo<GameModule> pageInfo =  PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> gameModuleMapper.selectList(wrapper));
	    return pageInfo;    
    }
	    
    @Override
    public void deleteModule(Long id, String account) {
        UpdateWrapper<GameModule> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(GameModule::getIsDelete, true)
                .set(GameModule::getUpdateUser, account)
                .eq(GameModule::getId, id);
        this.update(null, wrapper);
    }

}
