package com.onelive.common.mybatis.mapper.slave.sys;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.mybatis.entity.SysAdvFlashview;
import com.onelive.common.mybatis.entity.SysAdvFlashviewLang;

/**
 * <p>
 * 广告首页轮播表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface SlaveSysAdvFlashviewMapper extends BaseMapper<SysAdvFlashview> {

	/**
	 * 	用户端根据轮播图类型查询
	 * 
	 * @param flashviewType
	 * @param lang
	 * @param now 
	 * @return
	 */
	List<SysAdvFlashviewLang> getAdvByType(String flashviewType, String lang, Date now);

}
