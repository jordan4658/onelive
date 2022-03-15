package com.onelive.api.modules.game.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ob.constant.GameTypeConstant;
import com.ob.constant.VersionEnum;
import com.ob.internal.common.util.GsonUtil;
import com.ob.req.CheckMaintenanceReq;
import com.ob.req.ModifyBetLimitReq;
import com.ob.resp.BaseCommonResp;
import com.ob.resp.CheckMaintenanceResp;
import com.ob.service.GameService;
import com.onelive.api.service.game.GameCategoryService;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.game.GameZRModifyBetLimitDTO;
import com.onelive.common.model.req.game.GameZRCheckMaintenanceReq;
import com.onelive.common.mybatis.entity.GameCategory;
import com.onelive.common.utils.redis.GameRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 真人游戏业务类
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameZRBusiness {
    @Resource
    private GameCategoryService gameCategoryService;
    /**
     * 修改限红
     */
    public void modifyBetLimit(GameZRModifyBetLimitDTO dto) {
        log.info("执行修改限红....dto="+dto);
        ModifyBetLimitReq modifyBetLimitReq = new ModifyBetLimitReq();
        //用户名
        modifyBetLimitReq.setLoginName(dto.getAccno());
        //盘口类型
        modifyBetLimitReq.setOddType(dto.getOddType());
        //商户账号
        modifyBetLimitReq.setMerchantCode(dto.getMerchantCode());
        modifyBetLimitReq.setHost(dto.getInfoHost());
        modifyBetLimitReq.setAesKey(dto.getAesKey());
        modifyBetLimitReq.setMd5Key(dto.getMd5Key());
        modifyBetLimitReq.setGameTypeEnum(GameTypeConstant.ZR);
        //接口版本号 默认V1
        modifyBetLimitReq.setVersionEnum(VersionEnum.V1);
        //时间戳 当前时间13位时间戳
        modifyBetLimitReq.setTimestamp(System.currentTimeMillis());
        BaseCommonResp resp = GameService.getGameService().modifyBetLimit(modifyBetLimitReq);
        if(resp.getCode()!=200){
            throw new BusinessException(StatusCode.GAME_LOGIN_FAILURE);
        }
    }


    /**
     * 查询真人游戏维护状态
     */
    public CheckMaintenanceResp checkMaintenance(GameZRCheckMaintenanceReq req){
        if(req==null || req.getCategoryId()==null){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        GameCategory category = GameRedisUtils.getGameCategory(req.getCategoryId());
        if(category == null){
            QueryWrapper<GameCategory> categoryWrapper = new QueryWrapper<>();
            categoryWrapper.lambda().eq(GameCategory::getCategoryId, req.getCategoryId()).last(" LIMIT 1");
            category = gameCategoryService.getBaseMapper().selectOne(categoryWrapper);
            if (category == null) {
                throw new BusinessException(StatusCode.PARAM_ERROR);
            }
            GameRedisUtils.setGameCategory(category);
        }

        CheckMaintenanceReq checkMaintenanceReq = new CheckMaintenanceReq();
        checkMaintenanceReq.setMerchantCode(category.getMerchantCode());
        checkMaintenanceReq.setHost(category.getInfoHost());
        //接口版本号 默认V1
        checkMaintenanceReq.setVersionEnum(VersionEnum.V1);
        checkMaintenanceReq.setGameTypeEnum(GameTypeConstant.ZR);
        checkMaintenanceReq.setMd5Key(category.getMd5Key());
        checkMaintenanceReq.setAesKey(category.getAesKey());
        //时间戳 当前时间13位时间戳
        checkMaintenanceReq.setTimestamp(System.currentTimeMillis());
        CheckMaintenanceResp resp = GameService.getGameService().checkMaintenance(checkMaintenanceReq);
        System.out.println(GsonUtil.getInstance().toJson(resp));
        return resp;
    }
}
