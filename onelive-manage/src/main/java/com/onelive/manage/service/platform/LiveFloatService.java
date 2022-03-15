package com.onelive.manage.service.platform;

import com.onelive.common.model.dto.platform.LiveFloatDto;
import com.onelive.common.mybatis.entity.LiveFloat;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-20
 */
public interface LiveFloatService extends IService<LiveFloat> {

	List<LiveFloatDto> getList(LiveFloatDto liveFloatDto);

}
