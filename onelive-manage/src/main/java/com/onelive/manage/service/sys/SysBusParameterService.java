package com.onelive.manage.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.sys.SysBusParameterQueryReq;
import com.onelive.common.mybatis.entity.SysBusParameter;

import java.util.List;

/**
 * <p>
 * 业务参数 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-06
 */
public interface SysBusParameterService extends IService<SysBusParameter> {

    /**
     * 根据系统参数code获取唯一的业务参数
     *
     * @param code
     * @return
     */
    SysBusParameter getByCode(String code);

    /**
     * 根据父级系统参数获取业务参数列表
     *
     * @param pCode
     * @return
     */
    List<SysBusParameter> getChild(String pCode);

    /**
     * 查询业务参数列表
     *
     * @return
     */
    PageInfo<SysBusParameter> getList(SysBusParameterQueryReq param);

    /**
     * 删除业务参数
     *
     * @param id
     */
    void deleteParam(Long id, String account);

    /**
     * 新增业务参数
     *
     * @param p
     */
    void saveParam(SysBusParameter p);

    /**
     * 更新业务参数
     *
     * @param p
     */
    void updateParam(SysBusParameter p);

}
