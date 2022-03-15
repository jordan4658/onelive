package com.onelive.manage.service.lottery;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.lottery.MemberBetReq;
import com.onelive.common.model.req.operate.GameRiskReq;
import com.onelive.common.model.vo.lottery.MemberBetVO;
import com.onelive.common.model.vo.operate.GameRiskListVO;
import com.onelive.common.mybatis.entity.LotteryOrderBetRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户投注单-注号记录 服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
public interface LotteryOrderBetRecordService extends IService<LotteryOrderBetRecord> {

     List<MemberBetVO> getListOrderBet(MemberBetReq req);

     PageInfo<GameRiskListVO> countGameRiskListData(GameRiskReq req);
}
