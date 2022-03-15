package com.onelive.manage.modules.lottery.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.client.OneliveOrderClient;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.enums.OrderBetStatusEnum;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.SysParamEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.lottery.MemberBetReq;
import com.onelive.common.model.req.lottery.OrderCancleByIssueReq;
import com.onelive.common.model.req.lottery.OrderSettleByIdReq;
import com.onelive.common.model.req.lottery.OrderSettleByIssueReq;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.common.model.vo.lottery.*;
import com.onelive.common.mybatis.entity.*;
import com.onelive.manage.service.lottery.*;
import com.onelive.manage.service.mem.MemUserService;
import com.onelive.manage.service.sys.SysCountryService;
import com.onelive.manage.service.sys.SysParameterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemberBetBussiness {

    @Resource
    private LotteryOrderBetRecordService lotteryOrderBetRecordService;
    @Resource
    private LotteryOrderRecordService lotteryOrderRecordService;
    @Resource
    private MemUserService memUserService;
    @Resource
    private LotteryService lotteryService;
    @Resource
    private LotteryPlayService lotteryPlayService;
    @Resource
    private SysParameterService sysParameterService;
    @Resource
    private LotteryPlayOddsService lotteryPlayOddsService;
    @Resource
    private OneliveOrderClient oneliveOrderClient;
    @Resource
    private SysCountryService sysCountryService;

    /**
     * 注单订单列表
     *
     * @param req
     * @return
     */
    public PageInfo<MemberBetVO> getListOrderBet(MemberBetReq req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<MemberBetVO> voList = lotteryOrderBetRecordService.getListOrderBet(req);
        return new PageInfo<>(voList);

    }

    /** 根据订单id
     * @param orderId
     * @return
     */
    public OrderBetRecordDetailVO getOrderBetById(Integer orderId) {
        OrderBetRecordDetailVO vo = new OrderBetRecordDetailVO();
        if(orderId != null){
            LotteryOrderBetRecord record =  lotteryOrderBetRecordService.getById(orderId);
            LotteryOrderRecord parent = lotteryOrderRecordService.getById(record.getOrderId());
            Lottery  lottery =lotteryService.getLotteryByLotteryId(record.getLotteryId());
            LotteryPlay  lotteryPlay = lotteryPlayService.getById(record.getPid());//.queryLotteryPlayByPlayId(record.getPlayId());
            MemUser memUser =  memUserService.getById(record.getUserId());
            vo.setId(orderId);
            vo.setOrderNo(record.getOrderSn());
            vo.setUserId(Long.valueOf(record.getUserId()));
            vo.setNickName(memUser.getNickName());
            vo.setAccno(memUser.getAccno());
            vo.setIssue(record.getIssue());
            if(lottery != null){
                vo.setLotteryName(lottery.getName());
            }
            if(lotteryPlay != null){
                vo.setLotteryPlay(lotteryPlay.getName());
            }
            vo.setBackAmount(record.getBackAmount());
            vo.setBetAmount(record.getBetAmount());
            vo.setBetNumber(record.getBetNumber());
            if(parent.getCountryId() != null){
               SysCountry  sysCountry =  sysCountryService.getById(parent.getCountryId());
               vo.setCountryName(sysCountry.getZhName());
            }
            String odd;
            try {
                // 获取赔率因子
                SysParameter sysParameter = sysParameterService.getByCode(SysParamEnum.REGISTER_MEMBER_ODDS);
                double divisor = Double.parseDouble(sysParameter.getParamValue());
                // 获取赔率因子
                Double maxOdds = lottery.getMaxOdds();
                divisor = maxOdds.equals(0D) ? divisor : maxOdds;
                // 获取配置信息中的赔率信息
                odd = this.countOddsWithDivisor(record.getSettingId(), record.getBetNumber(), divisor);
                vo.setOdds(odd);
            } catch (Exception e) {
                log.error("getOrderMessageById 获取赔率因子报错", e);
            }
        }
        return vo;
    }

    /**
     * 根据用户id结算注单
     * @param req
     * @return
     */
    public ResultInfo<Boolean> settleOrderBetById( OrderSettleByIdReq req) {
        if(req == null
                || req.getId() == null
                || StringUtils.isBlank(req.getOpenNumber())
                || StringUtils.isBlank(req.getTbStatus())
                || StringUtils.isBlank(req.getWinAmount())

        ){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        Double winAmount = new BigDecimal(req.getWinAmount()).doubleValue();
        return oneliveOrderClient.jiesuanOrderBetById(req.getId(),winAmount,req.getTbStatus(),req.getOpenNumber());
    }

    /**
     * 根据期号结算订单
     * @param req
     * @return
     */
    public ResultInfo<Boolean> settleOrderBetByIssue(OrderSettleByIssueReq req){
        if(req == null
                || req.getLotteryId() == null
                || StringUtils.isBlank(req.getOpenNumber())
                || StringUtils.isBlank(req.getIssue())
        ){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        return oneliveOrderClient.jiesuanOrderBetByIssue(req.getLotteryId(),req.getIssue(),req.getOpenNumber());
    }


    /**
     * 根据期号取消订单
     * @param req
     * @return
     */
    public ResultInfo<Boolean> cancelOrderBetByIssue(OrderCancleByIssueReq req){
        if(req == null
                || req.getLotteryId() == null
                || StringUtils.isBlank(req.getIssue())
        ){
            throw new BusinessException(StatusCode.PARAM_ERROR);
        }
        return oneliveOrderClient.cancelOrderBetByIssue(req.getLotteryId(),req.getIssue());
    }


    /**
     * 获取中奖状态下拉列表
     * @return
     */
    public List<SelectStringVO> getBetStatusSelect(){
        List<SelectStringVO> list = new ArrayList<>();
        OrderBetStatusEnum[]  enums = OrderBetStatusEnum.values();
        for(int i = 0;i < enums.length ; i ++){
            SelectStringVO vo = new SelectStringVO();
            OrderBetStatusEnum bo = enums[i];
            vo.setValue(bo.getType());
            vo.setMsg(bo.getDesc());
            list.add(vo);
        }
        return list;
    }

    /**
     * 彩种下拉-玩法下拉返回类
     * @return
     */
    public List<BetLotterySelectVO> getBetLotterySelect(){
        List<BetLotterySelectVO> voList = new ArrayList<>();
        QueryWrapper<Lottery> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Lottery::getIsDelete,false);
        queryWrapper.lambda().orderByAsc(Lottery::getCategoryId);
        List<Lottery> lotteryList = lotteryService.queryLotteryWithLang(LangConstants.LANG_CN); // lotteryService.getSlave().selectList(queryWrapper);
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(lotteryList)){
            Map<Integer,List<LotteryPlayExVo>> lotteryPlayMap = new HashMap<>();
            List<LotteryPlayExVo> playList =lotteryPlayService.listLotteryPlayWithLang(null,LangConstants.LANG_CN); // lotteryPlayService.getSlave().selectList(null);
            if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(playList)){
                lotteryPlayMap =  playList.stream().collect(Collectors.groupingBy(LotteryPlayExVo::getLotteryId));
            }
            Iterator<Lottery> iterator = lotteryList.iterator();
            while (iterator.hasNext()){
                BetLotterySelectVO vo = new BetLotterySelectVO();
                Lottery lottery = iterator.next();
                vo.setLotteryId(lottery.getLotteryId());
                vo.setName(lottery.getName());
                List<LotteryPlayExVo> playLotteryList = lotteryPlayMap.get(lottery.getLotteryId());
                if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(playLotteryList)){
                    Iterator<LotteryPlayExVo> lotteryPlayIterator = playLotteryList.iterator();
                    List<BetLotterySelectChildVO> childVOList = new ArrayList<>();
                    while (lotteryPlayIterator.hasNext()){
                        LotteryPlayExVo lotteryPlay = lotteryPlayIterator.next();
                        BetLotterySelectChildVO playVo = new BetLotterySelectChildVO();
                        playVo.setName(lotteryPlay.getShowName());
                        playVo.setLotteryId(lotteryPlay.getLotteryId());
                        playVo.setPlayId(lotteryPlay.getId());
                        childVOList.add(playVo);
                    }
                    vo.setChildList(childVOList);
                }
                voList.add(vo);
            }
        }

        return voList;

    }


    ////////////////////////////////////////////私有方法///////////////////////////////////////////////////////////////

    /**
     * 计算赔率（乘以赔率因子）
     *
     * @param settingId 配置id
     * @param betNumber 投注号码
     * @param divisor   赔率因子
     * @return
     */
    private String countOddsWithDivisor(Integer settingId, String betNumber, double divisor) {
        if (betNumber.contains("@")) {
            betNumber = betNumber.split("@")[1];
        }

        // 通过配置获取赔率信息
        List<LotteryPlayOdds> oddsList = lotteryPlayOddsService.selectOddsListBySettingId(settingId);

        // 判空
        if (CollectionUtils.isEmpty(oddsList)) {
            return "";
        }
        LotteryPlayOdds odds = null;
        if (oddsList.size() == 1) {
            odds = oddsList.get(0);
        } else {
            TreeMap<Double, LotteryPlayOdds> maxOddstTreeMap = new TreeMap<>();
            for (LotteryPlayOdds playOdds : oddsList) {
                String[] splitBetNum = betNumber.split(",");
                // 普通玩法
                if (splitBetNum.length <= 1 && playOdds.getName().equals(betNumber)) {
                    // odds = playOdds;
                    maxOddstTreeMap.put(Double.parseDouble(playOdds.getTotalCount()) / Double.parseDouble(playOdds.getWinCount()), playOdds);
                } else {// TODO 特殊玩法（投注号码与后台设置内容不符）
                    for (String betContent : splitBetNum) {
                        if (playOdds.getName().equals(betContent)) {
                            // odds = playOdds;
                            maxOddstTreeMap.put(Double.parseDouble(playOdds.getTotalCount()) / Double.parseDouble(playOdds.getWinCount()), playOdds);
                        }
                    }
                }
            }
            // 获取最大odds
            int treeMapSize = maxOddstTreeMap.size();
            if (treeMapSize > 0) {
                odds = maxOddstTreeMap.get(maxOddstTreeMap.lastKey());
            }
        }

        if (odds == null) {
            return "";
        }

        String totalCount = odds.getTotalCount();
        BigDecimal winCount = BigDecimal.valueOf(Double.valueOf(odds.getWinCount()));
        String oddsStr;
        if (totalCount.contains("/")) {
            String[] str = totalCount.split("/");
            oddsStr = BigDecimal.valueOf(Double.valueOf(str[0])).multiply(BigDecimal.valueOf(divisor)).divide(winCount, 3, BigDecimal.ROUND_HALF_UP).toString();
        } else {
            oddsStr = BigDecimal.valueOf(Double.valueOf(totalCount)).multiply(BigDecimal.valueOf(divisor)).divide(winCount, 3, BigDecimal.ROUND_HALF_UP).toString();
        }
        return oddsStr;
    }

}
