package com.onelive.api.service.sys.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.sys.SysParameterService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.enums.SysParamEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.mybatis.mapper.master.sys.SysParametersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统参数 服务实现类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
@Service
@Slf4j
public class SysParameterServiceImpl extends ServiceImpl<SysParametersMapper, SysParameter> implements SysParameterService {


    @Override
    public SysParameter getByCode(SysParamEnum sysParamEnum) throws BusinessException {
        return getByCode(null == sysParamEnum ? null : sysParamEnum.getCode());
    }

    @Override
    public SysParameter getByCode(String code) {
        SysParameter sysParameter = ApiBusinessRedisUtils.getSysParameter(code);
        if (sysParameter == null) {
            // 写入缓存,读主库
            sysParameter = this.baseMapper.selectByCode(code);
            if(sysParameter!=null) {
                ApiBusinessRedisUtils.setSysParameter(code, sysParameter);
            }
        }
        return sysParameter;
    }



}
