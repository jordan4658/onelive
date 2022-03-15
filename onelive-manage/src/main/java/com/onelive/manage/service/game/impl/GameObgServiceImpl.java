package com.onelive.manage.service.game.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.GameObg;
import com.onelive.common.mybatis.mapper.master.game.GameObgMapper;
import com.onelive.manage.service.game.GameObgService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方OBG游戏表, 用于同步第三方游戏列表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-26
 */
@Service
public class GameObgServiceImpl extends ServiceImpl<GameObgMapper, GameObg> implements GameObgService {

}
