package com.onelive.api.service.sys;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.SysAdvFlashview;
import com.onelive.common.mybatis.entity.SysAdvFlashviewLang;

/**
 * <p>
 *	 广告首页轮播表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface SysAdvFlashviewService extends IService<SysAdvFlashview> {

	
	/**
	 * 	根据轮播图类型查询
	 * 
	 * @param flashviewType
	 * @return
	 */
	List<SysAdvFlashviewLang> getAdvByType(String flashviewType);
	

	
}
