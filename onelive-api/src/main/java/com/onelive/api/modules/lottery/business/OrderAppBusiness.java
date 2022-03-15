package com.onelive.api.modules.lottery.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.api.service.live.LiveStudioListService;
import com.onelive.api.service.lottery.LotteryOrderBetRecordService;
import com.onelive.api.service.mem.MemUserService;
import com.onelive.common.client.OneliveOrderClient;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.StudioStatusEnums;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.lottery.OrderLotteryDTO;
import com.onelive.common.model.req.lottery.OrderReq;
import com.onelive.common.model.req.order.BetInfoReq;
import com.onelive.common.model.vo.order.BetInfoVO;
import com.onelive.common.model.vo.order.LotteryOrderBetRecordVO;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.LotteryOrderBetRecord;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.mongodb.base.BaseMongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class OrderAppBusiness extends BaseMongoService {

    @Resource
    private LiveStudioListService liveStudioListService;
    @Resource
    private LotteryOrderBetRecordService lotteryOrderBetRecordService;
    @Resource
    private MemUserService memUserService;
    @Resource
    private OneliveOrderClient oneliveOrderClient;


    /**
     * 检查下注时候，彩票的订单状态
     * @param dto
     */
   public LiveStudioList checkOrder(OrderLotteryDTO dto) {
      String studioNum = dto.getStudioNum();
      LiveStudioList  studioList = liveStudioListService.getByRoomNum(studioNum);//.getById(studiostudioNum);
      //获取房间信息为空
      if(studioList == null) throw new BusinessException(StatusCode.LIVE_ROOM_BET_ERROR);

      //房间状态为已开播才能下注
      if(studioList.getStudioStatus() != StudioStatusEnums.OPENING.getCode()) {
          throw new BusinessException(StatusCode.LIVE_ROOM_BET_STATUS);
      }
      //检查用户状态
       MemUser memUser = memUserService.getById(dto.getUserId());
      if(memUser == null){
          throw new BusinessException(StatusCode.LIVE_ROOM_BET_ERROR);
      }
      //用户已被冻结
      if(memUser.getIsFrozen()){
          throw new BusinessException(StatusCode.USER_FROZEN);
      }
      //你的账户已被限制下注
      if(!memUser.getIsBet()){
          throw new BusinessException(StatusCode.USER_NOT_BET);
      }
      return studioList;
   }

    /**
     * 老彩种投注
     * @param req
     * @return
     */
   public ResultInfo orderBet(OrderReq req){
        //1、检测用户下单状态
        OrderLotteryDTO dto = new OrderLotteryDTO();
        dto.setUserId(LoginInfoUtil.getUserId());
        dto.setStudioNum((req.getStudioNum()));
        this.checkOrder(dto);

        //2、调用订单服务下注接口
        req.setUserId(LoginInfoUtil.getUserId().intValue());
        req.setSource(LoginInfoUtil.getSource());
        req.setCountryId(LoginInfoUtil.getCountryId());
        return  oneliveOrderClient.orderBet(req);
    }


    /**
     * 根据订单ID查询下注信息,用于跟投
     * @param req
     * @return
     */
    public BetInfoVO queryBetInfo(BetInfoReq req) {
        BetInfoVO vo = new BetInfoVO();
        //查询下注信息
        QueryWrapper<LotteryOrderBetRecord> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(LotteryOrderBetRecord::getOrderId,req.getOrderId());
        List<LotteryOrderBetRecord> list = lotteryOrderBetRecordService.list(queryWrapper);
        List<LotteryOrderBetRecordVO> voList = BeanCopyUtil.copyCollection(list, LotteryOrderBetRecordVO.class);
        vo.setBetList(voList);
        return vo;
    }
}
