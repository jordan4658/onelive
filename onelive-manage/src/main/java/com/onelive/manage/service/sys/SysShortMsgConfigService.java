package com.onelive.manage.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.mybatis.entity.SysShortMsgConfig;

/**
 * <p>
 * 短信配置 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-03
 */
public interface SysShortMsgConfigService extends IService<SysShortMsgConfig> {

	 /**
     * 分页查询
     */
    PageInfo<SysShortMsgConfig> getList(Integer pageNum, Integer pageSize);
    /**
     * 删除
     */
    void delete(Long id,String account);
}
