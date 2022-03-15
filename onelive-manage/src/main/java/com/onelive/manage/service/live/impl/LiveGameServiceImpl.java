package com.onelive.manage.service.live.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.LiveGame;
import com.onelive.common.mybatis.mapper.master.live.LiveGameMapper;
import com.onelive.manage.service.live.LiveGameService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 直播间游戏 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-06
 */
@Service
public class LiveGameServiceImpl extends ServiceImpl<LiveGameMapper, LiveGame> implements LiveGameService {

}
