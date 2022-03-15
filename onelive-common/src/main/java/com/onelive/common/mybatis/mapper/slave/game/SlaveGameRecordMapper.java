package com.onelive.common.mybatis.mapper.slave.game;

import com.onelive.common.model.req.game.GameRecordDetailReq;
import com.onelive.common.model.req.mem.MemUserGameRecordListReq;
import com.onelive.common.model.vo.game.GameRecordDetailVO;
import com.onelive.common.model.vo.game.GameRecordVO;
import com.onelive.common.mybatis.entity.GameRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.mybatis.sqlProvider.GameRecordSqlProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 第三方游戏记录 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-12
 */
public interface SlaveGameRecordMapper extends BaseMapper<GameRecord> {

    @SelectProvider(value = GameRecordSqlProvider.class, method = "sumUserBetRecordData")
    List<GameRecordVO> sumUserBetRecordData(MemUserGameRecordListReq req);

    @SelectProvider(value = GameRecordSqlProvider.class, method = "sumUserBetRecordDetailData")
    List<GameRecordDetailVO> sumUserBetRecordDetailData(GameRecordDetailReq req);
}
