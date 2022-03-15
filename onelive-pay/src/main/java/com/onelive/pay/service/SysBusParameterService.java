package com.onelive.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * 获取父code的列表数据
     *
     * @param pcode
     * @return
     */
    List<SysBusParameter> getByParentCode(String pcode);

    /**
     * 获取code对应的业务参数
     *
     * @param code
     * @return
     */
    SysBusParameter getByCode(String code);
}
