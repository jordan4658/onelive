package com.onelive.common.mybatis.mapper.slave.sys;

import java.util.Date;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.sys.SysAdvNoticeDto;
import com.onelive.common.mybatis.entity.SysAdvNotice;

/**
 * <p>
 * 广告公告表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface SlaveSysAdvNoticeMapper extends BaseMapper<SysAdvNotice> {

	SysAdvNoticeDto selectOneByType(Integer type, String lang, String merchantCode, Date now);

}
