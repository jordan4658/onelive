package com.onelive.manage.service.game.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.GameIndex;
import com.onelive.common.mybatis.mapper.master.game.GameIndexMapper;
import com.onelive.manage.service.game.GameIndexService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 首页游戏内容配置 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-06
 */
@Service
public class GameIndexServiceImpl extends ServiceImpl<GameIndexMapper, GameIndex> implements GameIndexService {

}
