package com.onelive.common.mybatis.mapper.slave.lottery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.req.lottery.MemberBetReq;
import com.onelive.common.model.req.operate.GameRiskReq;
import com.onelive.common.model.vo.lottery.MemberBetVO;
import com.onelive.common.model.vo.operate.GameRiskListVO;
import com.onelive.common.mybatis.entity.LotteryOrderBetRecord;
import com.onelive.common.mybatis.sqlProvider.GameRiskSqlProvider;
import com.onelive.common.mybatis.sqlProvider.MemberBetSqlProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 用户投注单-注号记录 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
public interface SlaveLotteryOrderBetRecordMapper extends BaseMapper<LotteryOrderBetRecord> {

    @SelectProvider(type = MemberBetSqlProvider.class, method = "getListOrderBet")
    List<MemberBetVO> getListOrderBet(MemberBetReq req);

    @SelectProvider(type = GameRiskSqlProvider.class, method = "countGameRiskListData")
    List<GameRiskListVO> countGameRiskListData(GameRiskReq req);

}
