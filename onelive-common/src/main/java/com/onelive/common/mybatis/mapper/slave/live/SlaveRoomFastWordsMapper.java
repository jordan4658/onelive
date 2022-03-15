package com.onelive.common.mybatis.mapper.slave.live;

import com.onelive.common.model.dto.live.RoomFastWordsDto;
import com.onelive.common.mybatis.entity.RoomFastWords;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-19
 */
public interface SlaveRoomFastWordsMapper extends BaseMapper<RoomFastWords> {

	List<RoomFastWords> getList(RoomFastWordsDto roomFastWordsDto);

}
