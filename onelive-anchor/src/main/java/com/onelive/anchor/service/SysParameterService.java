package com.onelive.anchor.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.enums.SysParamEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.mybatis.entity.SysParameter;

/**
 * <p>
 * 系统参数 服务类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SysParameterService extends IService<SysParameter> {


    /**
     * 获取系统配置
     *
     * @param SysParamEnum
     * @return
     * @throws BusinessException
     */
    SysParameter getByCode(SysParamEnum SysParamEnum) throws BusinessException;

    /**
     * 获取系统配置
     *
     * @param code
     * @return
     */
    SysParameter getByCode(String code);



}
