package com.onelive.manage.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.sys.appversion.SysAppVersionListReq;
import com.onelive.common.mybatis.entity.SysAppVersion;

/**
 * <p>
 * app版本管理 服务类
 * </p>
 */
public interface SysAppVersionService extends IService<SysAppVersion> {

	PageInfo<SysAppVersion> getAllList(SysAppVersionListReq req);
}
