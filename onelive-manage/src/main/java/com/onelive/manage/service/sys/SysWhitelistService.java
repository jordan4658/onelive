package com.onelive.manage.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.SysWhitelist;

/**
 * <p>
 * 白名单 服务类
 * </p>
 */
public interface SysWhitelistService extends IService<SysWhitelist> {

	 /**
     * 分页查询
     */
    PageInfo<SysWhitelist> getList(String  keyword,Integer pageNum, Integer pageSize);
    /**
     * 删除
     */
    void delete(Long id,String account);
}
