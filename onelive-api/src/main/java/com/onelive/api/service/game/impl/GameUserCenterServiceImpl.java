package com.onelive.api.service.game.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.game.GameUserCenterService;
import com.onelive.common.mybatis.entity.GameUserCenter;
import com.onelive.common.mybatis.mapper.master.game.GameUserCenterMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户中心游戏内容配置 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-07
 */
@Service
public class GameUserCenterServiceImpl extends ServiceImpl<GameUserCenterMapper, GameUserCenter> implements GameUserCenterService {

}
