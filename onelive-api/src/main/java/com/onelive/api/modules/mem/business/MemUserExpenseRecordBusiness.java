package com.onelive.api.modules.mem.business;

import com.onelive.api.service.mem.MemUserExpenseRecordService;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.UserExpenseTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.req.mem.MemUserExpenseRecordAddReq;
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
public class MemUserExpenseRecordBusiness {

    @Resource
    private MemUserExpenseRecordService memUserExpenseRecordService;


    /**
     * 添加消费记录
     *
     * @param req
     */
    public void addExpenseRecord(MemUserExpenseRecordAddReq req) {
        Long userId = LoginInfoUtil.getUserId();
        if (userId == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }
        BigDecimal amount = req.getAmount();
        UserExpenseTypeEnum type = req.getType();
        if (type == null) {
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        memUserExpenseRecordService.addExpenseRecord(userId, amount, type.getType(), req.getStudioNum());
    }
}
