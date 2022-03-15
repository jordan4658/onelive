package com.onelive.manage.service.live.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.dto.live.RoomFastWordsDto;
import com.onelive.common.mybatis.entity.RoomFastWords;
import com.onelive.common.mybatis.mapper.master.live.RoomFastWordsMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveRoomFastWordsMapper;
import com.onelive.manage.service.live.RoomFastWordsService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-19
 */
@Service
public class RoomFastWordsServiceImpl extends ServiceImpl<RoomFastWordsMapper, RoomFastWords> implements RoomFastWordsService {

	@Resource
	private SlaveRoomFastWordsMapper slaveRoomFastWordsMapper;
	
	@Override
	public List<RoomFastWords> getList(RoomFastWordsDto roomFastWordsDto) {
		return slaveRoomFastWordsMapper.getList(roomFastWordsDto);
	}

}
