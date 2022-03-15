package com.onelive.pay.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.platform.LiveGiftForIndexDto;
import com.onelive.common.mybatis.entity.LiveGift;

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
	 * 查询礼物列表
	 * @return
	 */
	List<LiveGiftForIndexDto> getList(String countryCode, String lang);




}
