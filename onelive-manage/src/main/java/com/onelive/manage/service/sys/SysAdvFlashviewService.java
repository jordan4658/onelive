package com.onelive.manage.service.sys;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.sys.SysAdvFlashviewVO;
import com.onelive.common.mybatis.entity.SysAdvFlashview;

/**
 * <p>
 * 广告首页轮播表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface SysAdvFlashviewService extends IService<SysAdvFlashview> {
	
	/**
	 * 后台管理查询 轮播图
	 */
	List<SysAdvFlashviewVO> getList();

	
}
