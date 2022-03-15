package com.onelive.api.service.game.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.game.GameTransferRecordService;
import com.onelive.common.mybatis.entity.GameTransferRecord;
import com.onelive.common.mybatis.mapper.master.game.GameTransferRecordMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方游戏上下分转账记录表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-23
 */
@Service
public class GameTransferRecordServiceImpl extends ServiceImpl<GameTransferRecordMapper, GameTransferRecord> implements GameTransferRecordService {

}
