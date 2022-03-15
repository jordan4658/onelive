package com.onelive.manage.modules.operate.business;

import com.github.pagehelper.PageInfo;
import com.onelive.common.client.TaskServerSgClient;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.operate.LotterySGListDTO;
import com.onelive.common.model.req.operate.LotterySGRecordReq;
import com.onelive.common.model.vo.operate.LotterySGListVO;
import com.onelive.common.model.vo.operate.LotterySGReocrdVO;
import com.onelive.common.mybatis.entity.Lottery;
import com.onelive.common.utils.others.DateUtils;
import com.onelive.manage.service.lottery.LotteryService;
import com.onelive.manage.utils.redis.LotteryBusinessRedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 开奖服务业务类
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LotterySGBusiness {

    @Resource
    private TaskServerSgClient taskServerSgClient;

    @Resource
    private LotteryService lotteryService;


    /**
     * 查询开奖记录
     * @param req
     * @return
     */
    public PageInfo<LotterySGReocrdVO> getSgRecordList(LotterySGRecordReq req) {

        ResultInfo<PageInfo<LotterySGReocrdVO>> recordList = taskServerSgClient.getSgRecordList(req);
        PageInfo<LotterySGReocrdVO> pageInfo = recordList.getData();

        if(pageInfo!=null){
            List<LotterySGReocrdVO> list = pageInfo.getList();
            if(CollectionUtils.isNotEmpty(list)){
                // tableName -> lottery
                Map<String, Lottery> lotteryMap = getLotteryTableMapFromCach();
                list.stream().forEach(vo->{
                    Lottery lottery = lotteryMap.get(vo.getGame());
                    vo.setName(lottery.getName());
                    Date date = DateUtils.parseDate(vo.getTime());
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    c.set(Calendar.SECOND,-lottery.getEndTime());
                    String fpTime = DateUtils.getYMDMms(c.getTime());
                    vo.setFpTime(fpTime);
                });
                return pageInfo;
            }
        }
        return new PageInfo<>();
    }

    /**
     * 从缓存中获取信息
     * @return
     */
    private Map<String, Lottery> getLotteryTableMapFromCach() {
        //tabbleName->Lottery
        Map<String, Lottery> map = LotteryBusinessRedisUtils.getLotteryMapInfo();
        if(map!=null){
            return map;
        }
        ResultInfo<List<LotterySGListDTO>> resultInfo = taskServerSgClient.getSgLotteryList();
        List<LotterySGListDTO> list = resultInfo.getData();
        if(CollectionUtils.isNotEmpty(list)) {
            List<Lottery> lotteryList = getLotteryList();
            if (CollectionUtils.isNotEmpty(lotteryList)) {
                Map<Integer, Lottery> lotteryMap = new HashMap<>();
                lotteryList.stream().forEach(lottery -> lotteryMap.put(lottery.getLotteryId(),lottery));
                map = new HashMap<>();
                for (LotterySGListDTO dto : list) {
                    map.put(dto.getTableName(),lotteryMap.get(dto.getLotteryId()));
                }
                LotteryBusinessRedisUtils.setLotteryMapInfo(map);
                return map;
            }
        }
        return new HashMap<>();
    }


    /**
     * 获取彩票信息
     * @return
     */
    private List<Lottery> getLotteryList() {
        List<Lottery> lotteryList = LotteryBusinessRedisUtils.getLotteryAllInfo();
        if (CollectionUtils.isEmpty(lotteryList)) {
            //查询数据库
            lotteryList = lotteryService.list();
            LotteryBusinessRedisUtils.setLotteryAllInfo(lotteryList);
        }
       return lotteryList;
    }


    /**
     * 查询有开奖记录的彩票
     * @return
     */
    public List<LotterySGListVO> getSgLotteryList() {
        ResultInfo<List<LotterySGListDTO>> resultInfo = taskServerSgClient.getSgLotteryList();
        List<LotterySGListDTO> list = resultInfo.getData();
        if(CollectionUtils.isNotEmpty(list)) {
            List<Lottery> lotteryList = getLotteryList();
            Map<Integer, String> lotteryMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(lotteryList)) {
                lotteryList.stream().forEach(lottery -> lotteryMap.put(lottery.getLotteryId(), lottery.getName()));
            }

            List<LotterySGListVO> voList = new LinkedList<>();

            list.stream().forEach(dto->{
                LotterySGListVO vo = new LotterySGListVO();
                vo.setName(lotteryMap.get(dto.getLotteryId()));
                vo.setGame(dto.getTableName());
                voList.add(vo);
            });
            return voList;
        }
        return new LinkedList<>();
    }
}
