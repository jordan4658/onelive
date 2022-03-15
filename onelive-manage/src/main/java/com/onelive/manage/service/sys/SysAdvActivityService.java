package com.onelive.manage.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.mybatis.entity.SysAdvActivity;

/**
 * <p>
 * 广告首页轮播表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-17
 */
public interface SysAdvActivityService extends IService<SysAdvActivity> {

    PageInfo<SysAdvActivity> getActivityList(PageReq req);

    void deleteActivity(Long id, String operateUser);
}
