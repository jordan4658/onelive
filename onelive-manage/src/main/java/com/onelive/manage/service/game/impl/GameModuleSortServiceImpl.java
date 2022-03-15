package com.onelive.manage.service.game.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.onelive.common.mybatis.entity.GameModuleSort;
import com.onelive.common.mybatis.mapper.master.game.GameModuleSortMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.game.GameModuleSortService;

/**
 * <p>
 * 游戏排序 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-29
 */
@Service
public class GameModuleSortServiceImpl extends ServiceImpl<GameModuleSortMapper, GameModuleSort> implements GameModuleSortService {

	    @Resource
	    private GameModuleSortMapper gameModuleSortMapper;
	
		@Override
		public PageInfo<GameModuleSort> getList(Long moduleId, Integer pageNum, Integer pageSize) {
		    LambdaQueryWrapper<GameModuleSort> wrapper = Wrappers.<GameModuleSort>lambdaQuery()
	                .eq(moduleId != null,  GameModuleSort::getModuleId, moduleId)
	                .eq(GameModuleSort::getMerchantCode, LoginInfoUtil.getMerchantCode());
		    PageInfo<GameModuleSort> pageInfo =  PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> gameModuleSortMapper.selectList(wrapper));
		    return pageInfo;    
	    }
		    
	    @Override
	    public void deleteModuleSort(String ids, String account) {
	    	List<Long> idList = Lists.newArrayList();
	    	for(String id:ids.split(",")) {
	    		if(StringUtils.isNotEmpty(id)) {
	    			idList.add(Long.valueOf(id));
	    		}
	    	}
	        UpdateWrapper<GameModuleSort> wrapper = new UpdateWrapper<>();
	        wrapper.lambda().set(GameModuleSort::getIsDelete, true)
	                .set(GameModuleSort::getUpdateUser, account)
	                .in(GameModuleSort::getId, idList);
	        this.update(null, wrapper);
	    }

}
