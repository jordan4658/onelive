package com.onelive.api.service.live.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.live.RoomFastWordsService;
import com.onelive.common.mybatis.entity.RoomFastWords;
import com.onelive.common.mybatis.mapper.master.live.RoomFastWordsMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;

/**
 * <p>
 *  	直播间快捷回复文字服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-19
 */
@Service
public class RoomFastWordsServiceImpl extends ServiceImpl<RoomFastWordsMapper, RoomFastWords> implements RoomFastWordsService {

	@Resource
	private RoomFastWordsMapper roomFastWordsMapper;
	@Override
	public List<RoomFastWords> getAll() {
		QueryWrapper<RoomFastWords> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(RoomFastWords :: getLang, LoginInfoUtil.getLang());
		return roomFastWordsMapper.selectList(queryWrapper);
	}

}
