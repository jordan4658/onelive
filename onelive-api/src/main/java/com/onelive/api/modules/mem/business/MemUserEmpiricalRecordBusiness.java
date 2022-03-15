package com.onelive.api.modules.mem.business;

import com.onelive.api.service.mem.MemUserEmpiricalRecordService;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.UserEmpiricalTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.req.mem.MemUserEmpiricalRecordAddReq;
import com.onelive.common.utils.Login.LoginInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemUserEmpiricalRecordBusiness {

    @Resource
    private MemUserEmpiricalRecordService memUserEmpiricalRecordService;

    /**
     * 增加经验值记录
     *
     * @param req
     */
    public void addEmpiricalRecord(MemUserEmpiricalRecordAddReq req) {
        Long userId = LoginInfoUtil.getUserId();
        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }
        UserEmpiricalTypeEnum type = req.getType();
        if(type==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        BigDecimal empiricalValue = req.getEmpiricalValue();
        memUserEmpiricalRecordService.addEmpiricalRecord(userId,empiricalValue, type.getType());
    }
}
