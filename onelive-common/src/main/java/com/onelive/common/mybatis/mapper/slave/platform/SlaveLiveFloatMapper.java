package com.onelive.common.mybatis.mapper.slave.platform;

import com.onelive.common.model.dto.platform.LiveFloatForIndexDto;
import com.onelive.common.mybatis.entity.LiveFloat;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-12-20
 */
public interface SlaveLiveFloatMapper extends BaseMapper<LiveFloat> {

	List<LiveFloatForIndexDto> getFloatList(String countryCode, String lang);

}
