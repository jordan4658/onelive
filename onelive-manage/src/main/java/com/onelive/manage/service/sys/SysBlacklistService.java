package com.onelive.manage.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.SysBlacklist;

/**
 * <p>
 * 用户黑名单 服务类
 * </p>
 */
public interface SysBlacklistService extends IService<SysBlacklist> {

	 /**
     * 分页查询
     */
    PageInfo<SysBlacklist> getList(String  keyword,Integer pageNum, Integer pageSize);
}
