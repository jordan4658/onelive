package com.onelive.api.service.platform;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.platform.LiveFloatForIndexDto;
import com.onelive.common.mybatis.entity.LiveFloatLang;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-20
 */
public interface LiveFloatLangService extends IService<LiveFloatLang> {

	/**
	 * 	直播间悬浮窗查询
	 * @param countryCode
	 * @param lang
	 * @return
	 */
	List<LiveFloatForIndexDto> getFloatList(String countryCode, String lang);


}
