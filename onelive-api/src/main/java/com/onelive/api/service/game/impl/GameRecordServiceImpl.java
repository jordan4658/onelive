package com.onelive.api.service.game.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.game.GameRecordService;
import com.onelive.common.model.req.game.GameRecordDetailReq;
import com.onelive.common.model.req.mem.MemUserGameRecordListReq;
import com.onelive.common.model.vo.game.GameRecordDetailVO;
import com.onelive.common.model.vo.game.GameRecordVO;
import com.onelive.common.mybatis.entity.GameRecord;
import com.onelive.common.mybatis.mapper.master.game.GameRecordMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 第三方游戏记录 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-14
 */
@Service
public class GameRecordServiceImpl extends ServiceImpl<GameRecordMapper, GameRecord> implements GameRecordService {

    @Resource
    private SlaveGameRecordMapper slaveGameRecordMapper;

    @Override
    public List<GameRecordVO> sumUserBetRecordData(MemUserGameRecordListReq req) {
        return slaveGameRecordMapper.sumUserBetRecordData(req);
    }

    @Override
    public List<GameRecordDetailVO> sumUserBetRecordDetailData(GameRecordDetailReq req) {
        return slaveGameRecordMapper.sumUserBetRecordDetailData(req);
    }
}
