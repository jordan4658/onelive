package com.onelive.manage.service.lottery.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.model.req.lottery.MemberBetReq;
import com.onelive.common.model.req.operate.GameRiskReq;
import com.onelive.common.model.vo.lottery.LotteryPlayExVo;
import com.onelive.common.model.vo.lottery.MemberBetVO;
import com.onelive.common.model.vo.operate.GameRiskListVO;
import com.onelive.common.mybatis.entity.Lottery;
import com.onelive.common.mybatis.entity.LotteryOrderBetRecord;
import com.onelive.common.mybatis.mapper.master.lottery.LotteryOrderBetRecordMapper;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryMapper;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryOrderBetRecordMapper;
import com.onelive.common.mybatis.mapper.slave.lottery.SlaveLotteryPlayMapper;
import com.onelive.manage.service.lottery.LotteryOrderBetRecordService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户投注单-注号记录 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Service
public class LotteryOrderBetRecordServiceImpl extends ServiceImpl<LotteryOrderBetRecordMapper, LotteryOrderBetRecord> implements LotteryOrderBetRecordService {

    @Resource
    private SlaveLotteryOrderBetRecordMapper slaveLotteryOrderBetRecordMapper;
    @Resource
    private SlaveLotteryMapper slaveLotteryMapper;
    @Resource
    private SlaveLotteryPlayMapper slaveLotteryPlayMapper;

    @Override
    public List<MemberBetVO> getListOrderBet(MemberBetReq req) {
        List<MemberBetVO>  voList = slaveLotteryOrderBetRecordMapper.getListOrderBet(req);
        if(CollectionUtils.isNotEmpty(voList)){
            Map<Integer,Lottery> lotteryMap = new HashMap<>();
            Map<Integer,LotteryPlayExVo> lotteryPlayMap = new HashMap<>();
            List<Lottery> lotteryList =slaveLotteryMapper.queryLotteryWithLang(LangConstants.LANG_CN); //  slaveLotteryMapper.selectList(null);
            List<LotteryPlayExVo> playList =slaveLotteryPlayMapper.listLotteryPlayWithLang(LangConstants.LANG_CN,null);// slaveLotteryPlayMapper.selectList(null);
            if(CollectionUtils.isNotEmpty(lotteryList)){
                lotteryMap =  lotteryList.stream().collect(Collectors.toMap(Lottery::getLotteryId, Function.identity(), (key1, key2) -> key2));
            }
            if(CollectionUtils.isNotEmpty(playList)){
                lotteryPlayMap = playList.stream().collect(Collectors.toMap(LotteryPlayExVo::getId,Function.identity(), (key1, key2) -> key2));
            }
            Iterator<MemberBetVO> iterator = voList.iterator();
            while (iterator.hasNext()){
                MemberBetVO vo = iterator.next();
                Lottery lottery = lotteryMap.get(vo.getLotteryId());
                if(lottery != null){
                    vo.setLotteryName(lottery.getName());
                }
                LotteryPlayExVo play = lotteryPlayMap.get(vo.getPid());
                if(play != null ){
                    vo.setLotteryPlay(play.getShowName());
                }
            }
        }
        return voList;
    }

    /**
     * 统计游戏风控数据
     * @param req
     * @return
     */
    @Override
    public PageInfo<GameRiskListVO> countGameRiskListData(GameRiskReq req) {
        return PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(()->slaveLotteryOrderBetRecordMapper.countGameRiskListData(req));
    }
}
