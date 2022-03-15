package com.onelive.api.service.game.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.api.service.game.GameOutsideGamesService;
import com.onelive.common.mybatis.entity.GameOutsideGames;
import com.onelive.common.mybatis.mapper.master.game.GameOutsideGamesMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 第三方游戏 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-29
 */
@Service
public class GameOutsideGamesServiceImpl extends ServiceImpl<GameOutsideGamesMapper, GameOutsideGames> implements GameOutsideGamesService {

	 @Resource
	 private GameOutsideGamesMapper gameOutsideGamesMapper;
		
	@Override
	public PageInfo<GameOutsideGames> getList(Integer pageNum, Integer pageSize) {
	    LambdaQueryWrapper<GameOutsideGames> wrapper = Wrappers.<GameOutsideGames>lambdaQuery()
                .eq(GameOutsideGames::getMerchantCode, LoginInfoUtil.getMerchantCode());
	    PageInfo<GameOutsideGames> pageInfo =  PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> gameOutsideGamesMapper.selectList(wrapper));
	    return pageInfo;    
    }
}
