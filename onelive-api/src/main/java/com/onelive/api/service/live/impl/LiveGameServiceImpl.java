package com.onelive.api.service.live.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.live.LiveGameService;
import com.onelive.common.model.req.live.AppLiveGameListReq;
import com.onelive.common.mybatis.entity.LiveGame;
import com.onelive.common.mybatis.mapper.master.live.LiveGameMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveGameMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private SlaveLiveGameMapper slaveLiveGameMapper;

    @Override
    public List<LiveGame> listWithLang(AppLiveGameListReq req) {
        return slaveLiveGameMapper.listWithLang(req);
    }
}
