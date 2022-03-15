package com.onelive.manage.service.game.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.GameType;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.common.mybatis.mapper.master.game.GameTypeMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.game.GameTypeService;

/**
 * <p>
 * 游戏类型表 服务实现类
 * </p>
 */
@Service
public class GameTypeServiceImpl extends ServiceImpl<GameTypeMapper, GameType> implements GameTypeService {

    @Resource
    private GameTypeMapper gameTypeMapper;
	 /**
     * 分页查询
     */
    @Override
    public PageInfo<GameType> getList(Long countryId, Integer pageNum, Integer pageSize){
    		  LambdaQueryWrapper<GameType> wrapper = Wrappers.<GameType>lambdaQuery()
    	                .eq(countryId != null,  GameType::getCountryId, countryId)
    	                .eq(GameType::getMerchantCode, LoginInfoUtil.getMerchantCode());
    	        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> gameTypeMapper.selectList(wrapper));
    }
    
    @Override
    public void deleteType(Long id, String account) {
        UpdateWrapper<GameType> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(GameType::getIsDelete, true)
                .set(GameType::getUpdateUser, account)
                .eq(GameType::getId, id);
        this.update(null, wrapper);
    }
  
}
