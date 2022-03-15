package com.onelive.manage.modules.lottery.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.req.lottery.LotteryKillOrdersReq;
import com.onelive.common.model.vo.lottery.LotteryKillOrdersVO;
import com.onelive.common.model.vo.lottery.LotteryPlatfomVO;
import com.onelive.common.mybatis.entity.LotteryKillConfig;
import com.onelive.common.utils.redis.SysBusinessRedisUtils;
import com.onelive.manage.service.lottery.LotteryKillConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LotteryKillOrderBusiness {

    @Resource
    private LotteryKillConfigService lotteryKillConfigService;

    /**
     * 杀号配置列表
     * @return
     */
    public List<LotteryKillOrdersVO> queryKillList(){
        QueryWrapper<LotteryKillConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(LotteryKillConfig::getPlatfom).orderByAsc(LotteryKillConfig::getLotteryId);
        List<LotteryKillConfig> list = lotteryKillConfigService.getBaseMapper().selectList(queryWrapper);
        List<LotteryKillOrdersVO> allList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            Iterator<LotteryKillConfig> iterator = list.iterator();
            while (iterator.hasNext()){
                LotteryKillOrdersVO vo = new LotteryKillOrdersVO();
                LotteryKillConfig bo = iterator.next();
                vo.setId(bo.getId());
                vo.setLotteryId(bo.getLotteryId());
                vo.setName(bo.getName());
                vo.setPlatfom(bo.getPlatfom());
                vo.setRatio(bo.getRatio().toString());
                allList.add(vo);
            }
        }
        return allList;
    }

    /**
     * 更新杀号配置信息
      * @param req
     */
    public void updateKillConfig(LotteryKillOrdersReq req){
        if(req == null ||req.getId()==null ){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LotteryKillConfig config = lotteryKillConfigService.getById(req.getId());
        if(config == null){
            throw new BusinessException(StatusCode.PLAY_KILL_NOT_EXISTS);
        }
        config.setPlatfom(req.getPlatfom());
        BigDecimal ratio = new BigDecimal(req.getRatio());
        config.setRatio(ratio.doubleValue());
        lotteryKillConfigService.updateById(config);

        //清除杀号缓存
        SysBusinessRedisUtils.deleteAllKillConfig();
    }

    /**
     * 获取平台标识列表
     * @return
     */
    public List<LotteryPlatfomVO> queryPlatform(){
        List<LotteryPlatfomVO> voList = new ArrayList<>();
        LotteryPlatfomVO vo = new LotteryPlatfomVO();
        vo.setName("ONELIVE");
        vo.setPlatfom("ONELIVE");
        voList.add(vo);
        return voList;
    }


}
