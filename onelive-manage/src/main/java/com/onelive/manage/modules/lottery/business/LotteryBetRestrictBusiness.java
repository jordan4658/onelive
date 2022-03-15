package com.onelive.manage.modules.lottery.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.req.lottery.LotteryRestrictEditReq;
import com.onelive.common.model.vo.lottery.LotteryPlayExVo;
import com.onelive.common.model.vo.lottery.RestrictLotteryCategoryVO;
import com.onelive.common.model.vo.lottery.RestrictLotteryPlayVO;
import com.onelive.common.model.vo.lottery.RestrictLotteryVO;
import com.onelive.common.mybatis.entity.Lottery;
import com.onelive.common.mybatis.entity.LotteryBetRestrict;
import com.onelive.common.mybatis.entity.LotteryCategory;
import com.onelive.common.mybatis.entity.LotteryPlay;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.lottery.LotteryBetRestrictService;
import com.onelive.manage.service.lottery.LotteryCategoryService;
import com.onelive.manage.service.lottery.LotteryPlayService;
import com.onelive.manage.service.lottery.LotteryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LotteryBetRestrictBusiness {

    @Resource
    private LotteryCategoryService lotteryCategoryService;
    @Resource
    private LotteryService lotteryService;
    @Resource
    private LotteryPlayService lotteryPlayService;
    @Resource
    private LotteryBetRestrictService lotteryBetRestrictService;


    /**
     * 投注限制設置-彩种类型下拉-彩种名称下拉
     * @return
     */
    public List<RestrictLotteryCategoryVO> getRestrictLotteryList(){
        List<RestrictLotteryCategoryVO> voList = new ArrayList<>();

        QueryWrapper<LotteryCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryCategory::getIsDelete,false);
        List<LotteryCategory> list = lotteryCategoryService.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            Iterator<LotteryCategory> iterator = list.iterator();
            while (iterator.hasNext()){
                LotteryCategory bo = iterator.next();
                RestrictLotteryCategoryVO vo = new RestrictLotteryCategoryVO();
                BeanCopyUtil.copyProperties(bo,vo);

                List<Lottery> lotteryList = lotteryService.getLotteryByCategoryId(vo.getCategoryId());
                if(CollectionUtils.isNotEmpty(lotteryList)){
                    List<RestrictLotteryVO> childList = new ArrayList<>();
                    Iterator<Lottery> iteratorLottery = lotteryList.iterator();
                    while (iteratorLottery.hasNext()){
                        Lottery lottery = iteratorLottery.next();
                        RestrictLotteryVO lotteryVO = new RestrictLotteryVO();
                        BeanCopyUtil.copyProperties(lottery,lotteryVO);
                        childList.add(lotteryVO);
                    }
                    vo.setChildList(childList);
                }
                voList.add(vo);
            }
        }
        return voList;
    }

    /**
     * 通过彩种id获取玩法列表
     * @param lotteryId
     * @return
     */
    public List<RestrictLotteryPlayVO> getPlayList(Integer lotteryId){
        List<RestrictLotteryPlayVO> returnList = new ArrayList<>();
        QueryWrapper<LotteryPlay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryPlay::getLotteryId,lotteryId);
        queryWrapper.lambda().eq(LotteryPlay::getIsDelete,false);
        List<LotteryPlayExVo> list  = lotteryPlayService.listLotteryPlayWithLang(lotteryId, LangConstants.LANG_CN);//.list(queryWrapper);
        if(CollectionUtils.isNotEmpty(list)){
            Iterator<LotteryPlayExVo> iterator = list.iterator();
            while (iterator.hasNext()){
                RestrictLotteryPlayVO vo = new RestrictLotteryPlayVO();
                LotteryPlayExVo play = iterator.next();
                vo.setLotteryId(play.getLotteryId());
                vo.setName(play.getShowName());
                vo.setPlayId(play.getPlayTagId());
                LotteryBetRestrict restrict = lotteryBetRestrictService.getBetRestrictByPlayId(play.getPlayTagId());
                if(restrict != null){
                    vo.setMaxMoney(restrict.getMaxMoney());
                }
                returnList.add(vo);
            }
        }
        return returnList;
    }

    /**
     * 保存投注金额限制
     * @param req
     */
    public BigDecimal savaBetRestrict(LotteryRestrictEditReq req){
        // 玩法请求类
        if (req == null || req.getPlayId() == null || StringUtils.isBlank(req.getMaxMoney())){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        LotteryBetRestrict restrict = lotteryBetRestrictService.getBetRestrictByPlayId(req.getPlayId());
        Date now = new Date();

        if(restrict == null){
            restrict = new LotteryBetRestrict();
            restrict.setCrateTime(now);
            restrict.setUpdateTime(now);
            restrict.setLotteryId(req.getLotteryId());
            restrict.setPlayTagId(req.getPlayId());
            restrict.setMaxMoney(new BigDecimal(req.getMaxMoney()));
            lotteryBetRestrictService.save(restrict);
        }else{
            restrict.setUpdateTime(now);
            restrict.setMaxMoney(new BigDecimal(req.getMaxMoney()));
            lotteryBetRestrictService.updateById(restrict);
        }

        return restrict.getMaxMoney();

    }

}
