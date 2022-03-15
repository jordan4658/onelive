package com.onelive.manage.service.game.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.GameOutsideGamesRate;
import com.onelive.common.mybatis.mapper.master.game.GameOutsideGamesRateMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.service.game.GameOutsideGamesRateService;

/**
 * <p>
 * 第三方游戏汇率 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-29
 */
@Service
public class GameOutsideGamesRateServiceImpl extends ServiceImpl<GameOutsideGamesRateMapper, GameOutsideGamesRate> implements GameOutsideGamesRateService {
	 @Resource
	 private GameOutsideGamesRateMapper gameOutsideGamesRateMapper;
		
	@Override
	public PageInfo<GameOutsideGamesRate> getList(Long gameId, Integer pageNum, Integer pageSize) {
	    LambdaQueryWrapper<GameOutsideGamesRate> wrapper = Wrappers.<GameOutsideGamesRate>lambdaQuery()
                .eq(gameId != null,  GameOutsideGamesRate::getGameId, gameId)
                .eq(GameOutsideGamesRate::getMerchantCode, LoginInfoUtil.getMerchantCode());
	    PageInfo<GameOutsideGamesRate> pageInfo =  PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> gameOutsideGamesRateMapper.selectList(wrapper));
	    return pageInfo;    
    }
}
