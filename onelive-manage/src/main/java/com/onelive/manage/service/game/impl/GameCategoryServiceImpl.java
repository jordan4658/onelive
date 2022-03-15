package com.onelive.manage.service.game.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.GameCategory;
import com.onelive.common.mybatis.entity.GameThirdPlatform;
import com.onelive.common.mybatis.mapper.master.game.GameCategoryMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameCategoryMapper;
import com.onelive.common.utils.others.CollectionUtil;
import com.onelive.manage.service.game.GameCategoryService;
import com.onelive.manage.service.game.GameThirdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-22
 */
@Service
public class GameCategoryServiceImpl extends ServiceImpl<GameCategoryMapper, GameCategory>
		implements GameCategoryService {

	@Resource
	private GameThirdService gameThirdService;

	@Resource
	private SlaveGameCategoryMapper slaveGameCategoryMapper;

	@Override
	public List<GameCategory> getList() {
		QueryWrapper<GameCategory> queryWrapper = new QueryWrapper<GameCategory>();
		queryWrapper.lambda().eq(GameCategory :: getIsDelete, false);
		return slaveGameCategoryMapper.selectList(queryWrapper);
	}

	@Override
	public GameCategory getByCategoryId(Integer categoryId) {
		QueryWrapper<GameCategory> queryWrapper = new QueryWrapper<GameCategory>();
		queryWrapper.lambda().eq(GameCategory :: getIsDelete, false).eq(GameCategory::getCategoryId,categoryId).last(" LIMIT 1");
		return slaveGameCategoryMapper.selectOne(queryWrapper);
	}

	@Override
	public void updateStatusByPlatform(GameThirdPlatform platform) {
		if(platform==null){
			return;
		}
		//查询平台下的所有分类
		QueryWrapper<GameCategory> queryWrapper = new QueryWrapper<GameCategory>();
		queryWrapper.lambda().eq(GameCategory :: getIsDelete, false).eq(GameCategory::getPlatformCode,platform.getPlatformCode());
		List<GameCategory> categoryList = slaveGameCategoryMapper.selectList(queryWrapper);
		if(CollectionUtil.isNotEmpty(categoryList)){
			int status = platform.getIsShow()?2:1;
			for (GameCategory category : categoryList){
				if(category.getStatus().equals(status)){
					continue;
				}
				category.setStatus(status);
				//根据分类状态更新游戏状态
				this.updateById(category);
				if(category.getIsWork()) {
					gameThirdService.updateStatusByCategory(category);
				}
			}
		}
	}

}
