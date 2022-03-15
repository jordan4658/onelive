package com.onelive.manage.service.sys;

import com.onelive.common.mybatis.entity.SysImageInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 图片管理表 服务类
 * </p>
 *
 * @author ${author}
 */
public interface SysImageInfoService extends IService<SysImageInfo> {


	public void updateStatus(Long id, String account);
}
