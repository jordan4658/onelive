package com.onelive.api.service.game;

import com.onelive.common.model.req.game.GameRecordDetailReq;
import com.onelive.common.model.req.mem.MemUserGameRecordListReq;
import com.onelive.common.model.vo.game.GameRecordDetailVO;
import com.onelive.common.model.vo.game.GameRecordVO;
import com.onelive.common.mybatis.entity.GameRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 第三方游戏记录 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-14
 */
public interface GameRecordService extends IService<GameRecord> {

    /**
     * 查询第三方游戏记录列表统计
     * @param req
     * @return
     */
    List<GameRecordVO> sumUserBetRecordData(MemUserGameRecordListReq req);

    /**
     * 查询第三方游戏记录详情统计
     * @param req
     * @return
     */
    List<GameRecordDetailVO> sumUserBetRecordDetailData(GameRecordDetailReq req);
}
