package com.onelive.manage.service.platform;

import com.onelive.common.model.dto.platform.LiveGiftDto;
import com.onelive.common.mybatis.entity.LiveGift;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 礼物信息表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-03
 */
public interface LiveGiftService extends IService<LiveGift> {

	/**
	 * 后台管理查询礼物列表
	 * @param liveGiftDto
	 * @return
	 */
	List<LiveGiftDto> getList(LiveGiftDto liveGiftDto);
	
	/**
	 * 	获取弹幕类型的礼物
	 * @return
	 */
	LiveGift getBarrage();

}
