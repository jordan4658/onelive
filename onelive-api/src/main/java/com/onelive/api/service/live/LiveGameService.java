package com.onelive.api.service.live;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.req.live.AppLiveGameListReq;
import com.onelive.common.mybatis.entity.LiveGame;

import java.util.List;

/**
 * <p>
 * 直播间游戏 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-06
 */
public interface LiveGameService extends IService<LiveGame> {

    List<LiveGame> listWithLang(AppLiveGameListReq req);
}
