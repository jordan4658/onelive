package com.onelive.manage.service.platform;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.platform.PlatformShareConfigDto;
import com.onelive.common.mybatis.entity.PlatformShareConfig;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-23
 */
public interface PlatformShareConfigService extends IService<PlatformShareConfig> {

	List<PlatformShareConfigDto> getList();

}
