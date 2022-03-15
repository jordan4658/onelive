package com.onelive.manage.modules.lottery.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.req.lottery.LotteryCountryReq;
import com.onelive.common.model.req.lottery.LotteryCountryUpdateReq;
import com.onelive.common.model.req.lottery.LotteryCountryUpdateStatusReq;
import com.onelive.common.model.vo.lottery.LotteryCountryVO;
import com.onelive.common.mybatis.entity.Lottery;
import com.onelive.common.mybatis.entity.LotteryCountry;
import com.onelive.common.utils.upload.AWSS3Util;
import com.onelive.manage.service.lottery.LotteryCountryService;
import com.onelive.manage.service.lottery.LotteryService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LotteryCountryBusiness {

    @Resource
    private LotteryCountryService lotteryCountryService;
    @Resource
    private LotteryService lotteryService;

    /**
     * 直播游戏返回列表
     * @param req
     * @return
     */
    public List<LotteryCountryVO> queryLotteryCountry(LotteryCountryReq req){
        List<LotteryCountryVO> voList = new ArrayList<>();
        QueryWrapper<LotteryCountry> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryCountry::getCountryId,req.getCountryId());
        List<LotteryCountry>  list =  lotteryCountryService.list(queryWrapper);
        
        if(CollectionUtils.isNotEmpty(list)){
            List<Lottery> allLottertList = lotteryService.list();
            // LotteryId key ： Lottery value
            Map<Integer, Lottery> anchorMap = allLottertList.stream().collect(Collectors.toMap(Lottery::getLotteryId, a -> a,(k1,k2)->k1));
            
            Iterator<LotteryCountry> iterator = list.iterator();
            while (iterator.hasNext()){
            	LotteryCountry country  = iterator.next();
            	Lottery lottery = anchorMap.get(country.getLotteryId());
            	if(lottery == null) {
            		continue;
            	}
                LotteryCountryVO vo = new LotteryCountryVO();
                vo.setId(country.getId());
                vo.setName(country.getAliasName());
                vo.setIconUrl(AWSS3Util.getAbsoluteUrl(country.getIconUrl()));
                vo.setIsForbid(country.getIsForbid());
                vo.setStartTime("00:00:00");
                vo.setEndTime("23:59:00");
                Integer Interval = lottery.getStartlottoTimes() == null ? 0 : 1440 / lottery.getStartlottoTimes();
                vo.setInterval(Interval);
                vo.setClosingTime(lottery.getEndTime());
                vo.setIssueNum(lottery.getStartlottoTimes());
                voList.add(vo);
            }
        }
       return voList;
    }

    /**
     * 更新游戏信息
     * @param req
     */
    public void updateInfo(LotteryCountryUpdateReq req){
        if(req == null || req.getId() == null
           || req.getIsForbid() == null
           || StringUtils.isBlank(req.getIconUrl())
        ){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }

        LotteryCountry lotteryCountry =  lotteryCountryService.getById(req.getId());
        if(lotteryCountry == null){
            throw new BusinessException("找不到对应数据");
        }
        lotteryCountry.setIsForbid(req.getIsForbid());
        lotteryCountry.setIconUrl(AWSS3Util.getRelativeUrl(req.getIconUrl()));
        lotteryCountryService.updateById(lotteryCountry);
    }

    /**
     * 更新游戏状态
     * @param req
     */
    public void changeStatus(LotteryCountryUpdateStatusReq req){
        if(req == null || req.getId() == null
                || req.getIsForbid() == null
        ){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LotteryCountry lotteryCountry =  lotteryCountryService.getById(req.getId());
        if(lotteryCountry == null){
            throw new BusinessException("找不到对应数据");
        }
        lotteryCountry.setIsForbid(req.getIsForbid());
        lotteryCountryService.updateById(lotteryCountry);
    }


}
