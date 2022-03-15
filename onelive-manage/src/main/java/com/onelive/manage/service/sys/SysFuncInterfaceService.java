package com.onelive.manage.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.SysFuncInterface;

import java.util.List;

/**
 * <p>
 * 功能接口关系 服务类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SysFuncInterfaceService extends IService<SysFuncInterface> {

    /**
     * 根据角色获取角色模块对应的接口
     *
     * @param param
     * @return
     */
    List<String> getInterfaceUrlsByRole(List<Long> param);

}
