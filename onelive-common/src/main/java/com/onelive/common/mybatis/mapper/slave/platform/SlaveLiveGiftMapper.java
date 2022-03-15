package com.onelive.common.mybatis.mapper.slave.platform;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.platform.LiveGiftDto;
import com.onelive.common.model.dto.platform.LiveGiftForIndexDto;
import com.onelive.common.mybatis.entity.LiveGift;

/**
 * <p>
 * 礼物信息表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-03
 */
public interface SlaveLiveGiftMapper extends BaseMapper<LiveGift> {

	List<LiveGiftDto> getList(LiveGiftDto liveGiftDto);
	
	/**
	 * 	用户端礼物查询
	 * 
	 * @return
	 */
	List<LiveGiftForIndexDto> getListForIndex(String countryCode, String lang);

	LiveGiftDto getByIdAndLang(Integer giftId, String lang);

	List<LiveGiftForIndexDto> getLiveRoomList(String countryCode, String lang);
}
