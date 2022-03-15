package com.onelive.manage.modules.lottery.business;

import cn.hutool.core.util.StrUtil;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.SysParamEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.lottery.LotteryNoticeConfigDTO;
import com.onelive.common.model.req.lottery.LotteryNoticeConfigSaveReq;
import com.onelive.common.model.vo.lottery.LotteryNoticeConfigVO;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.JacksonUtil;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 彩票中奖公告配置业务类
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LotteryNoticeConfigBusiness {

    @Resource
    private SysParameterService sysParameterService;


    /**
     * 获取配置信息
     * @return
     */
    public LotteryNoticeConfigVO getConfig() {
        LotteryNoticeConfigVO vo=new LotteryNoticeConfigVO();
        SysParameter noticeConfig = sysParameterService.getByCode(SysParamEnum.LOTTERY_NOTICE_CONFIG);
        if(noticeConfig!=null){
            String json = noticeConfig.getParamValue();
            if(StrUtil.isNotBlank(json)){
                LotteryNoticeConfigDTO dto = JacksonUtil.fromJson(json,LotteryNoticeConfigDTO.class);
                BeanCopyUtil.copyProperties(dto,vo);
            }
        }
        return vo;
    }

    /**
     * 保存配置信息
     * @param req
     * @param loginUser
     */
    public void saveConfig(LotteryNoticeConfigSaveReq req, LoginUser loginUser) {
        if(loginUser==null){
            throw new BusinessException(401,"没有操作权限");
        }
        if(req.getShowTime()<=0 || req.getShowTime()>60){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        if(req.getAmount().compareTo(new BigDecimal("0"))<=0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        if(req.getCount() <= 0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        if(req.getLotteryIdList()==null || req.getLotteryIdList().size()==0){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        String admin = loginUser.getAccLogin();
        SysParameter noticeConfig = sysParameterService.getByCode(SysParamEnum.LOTTERY_NOTICE_CONFIG);
        if(noticeConfig==null){
            noticeConfig = new SysParameter();
            noticeConfig.setParamName(SysParamEnum.LOTTERY_NOTICE_CONFIG.getRemark());
            noticeConfig.setParamCode(SysParamEnum.LOTTERY_NOTICE_CONFIG.getCode());
            noticeConfig.setParamType(SysParamEnum.LOTTERY_NOTICE_CONFIG.getCode());
            noticeConfig.setRemark(SysParamEnum.LOTTERY_NOTICE_CONFIG.getRemark());
        }
        LotteryNoticeConfigDTO dto = new LotteryNoticeConfigDTO();
        BeanCopyUtil.copyProperties(req,dto);
        String json = JacksonUtil.toJson(dto);
        noticeConfig.setParamValue(json);
        if(noticeConfig.getParamId()==null){
            noticeConfig.setCreateUser(admin);
            sysParameterService.save(noticeConfig);
        }else{
            noticeConfig.setUpdateUser(admin);
            sysParameterService.updateById(noticeConfig);
        }
        //更新缓存
        SystemRedisUtils.addSysParameter(noticeConfig);
    }
}
