package com.onelive.manage.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.sys.SysAdvNoticeQueryReq;
import com.onelive.common.model.vo.sys.SysAdvNoticeVO;
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

	PageInfo<SysAdvNoticeVO> getAllList(SysAdvNoticeQueryReq req);
	
}
