package com.onelive.common.business.user;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.req.mem.SetWithdrawPasswordReq;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.service.mem.CommonMemUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class SetPayPasswordBusiness {

    @Resource
    private CommonMemUserService commonMemUserService;

    public void setPayPassword(SetWithdrawPasswordReq req, Long userId) {
        if (StringUtils.isBlank(req.getPassword())) {
            throw new BusinessException("支付密码为空！");
        }
        if (StringUtils.isBlank(req.getConfirmPassword())) {
            throw new BusinessException("二次确认支付密码为空！");
        }
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new BusinessException("两次密码不一致！");
        }
        String md5Password = DigestUtils.md5Hex(req.getPassword()).toLowerCase();
        UpdateWrapper<MemUser> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda().set(MemUser::getPayPassword, md5Password);
        updateWrapper.lambda().set(MemUser::getUpdateTime, new Date());
        updateWrapper.lambda().set(MemUser::getUserAccount, new Date());
        updateWrapper.lambda().eq(MemUser::getId, userId);
        commonMemUserService.update(updateWrapper);
    }
}
