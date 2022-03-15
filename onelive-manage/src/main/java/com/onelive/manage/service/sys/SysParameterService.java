package com.onelive.manage.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.enums.SysParamEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.common.mybatis.entity.SysParameter;

import java.util.List;

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


    /**
     * 按照id查询系统参数,确保唯一
     *
     * @param id
     * @return
     */
    SysParameter getById(Long id);

    /**
     * 修改系统参数
     *
     * @param parameter
     */
    void updateSysParameter(SysParameter parameter);

    /**
     * 分页获取系统配置
     *
     * @param code
     * @return
     */
    List<SysParameterListVO> getList(String code);
    
    /**
     * 根据type查询
     * 
     * @param type
     * @return
     */
    List<SysParameterListVO> getListByType(String type);
    
    /**
     * 		TODO:用到再加
     * 		根据code,type查询 查询唯一值
     * 
     * @param type
     * @return
     */
//    SysParameterListVO getByCodeAndType(String code, String type);

    /**
     * 更新删除状态
     *
     * @param id
     */
    void updateDeleteStatus(Long id);


}
