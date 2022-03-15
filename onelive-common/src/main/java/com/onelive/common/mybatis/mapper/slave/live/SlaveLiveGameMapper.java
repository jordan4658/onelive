package com.onelive.common.mybatis.mapper.slave.live;

import com.onelive.common.model.req.live.AppLiveGameListReq;
import com.onelive.common.mybatis.entity.LiveGame;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.mybatis.sqlProvider.LiveGameProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 直播间游戏 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-06
 */
public interface SlaveLiveGameMapper extends BaseMapper<LiveGame> {

    @SelectProvider(value = LiveGameProvider.class, method = "listWithLang")
    List<LiveGame> listWithLang(AppLiveGameListReq req);
}
