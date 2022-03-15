package com.onelive.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.sys.SysAdvNoticeDto;
import com.onelive.common.mybatis.entity.SysAdvNotice;

/**
 * <p>
 * 广告公告表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface SysAdvNoticeService extends IService<SysAdvNotice> {

	SysAdvNoticeDto selectOneByType(Integer type, String lang);

}
