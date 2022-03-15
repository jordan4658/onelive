package com.onelive.api.service.platform;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.index.IndexTopGameListDTO;
import com.onelive.common.mybatis.entity.GameIndex;

import java.util.List;

/**
 * <p>
 * 首页游戏内容配置 服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-01-06
 */
public interface GameIndexService extends IService<GameIndex> {

    List<IndexTopGameListDTO> queryIndexTopGameList();
}
