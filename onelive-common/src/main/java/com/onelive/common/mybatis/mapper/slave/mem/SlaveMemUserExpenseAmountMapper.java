package com.onelive.common.mybatis.mapper.slave.mem;

import com.onelive.common.model.dto.ranking.ContributionRankingDto;
import com.onelive.common.model.vo.ranking.RankingQueryVo;
import com.onelive.common.mybatis.entity.MemUserExpenseAmount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
public interface SlaveMemUserExpenseAmountMapper extends BaseMapper<MemUserExpenseAmount> {

    List<ContributionRankingDto> getContributionRankingList(RankingQueryVo queryVo);
}
