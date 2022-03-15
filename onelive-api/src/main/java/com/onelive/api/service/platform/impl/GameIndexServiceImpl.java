package com.onelive.api.service.platform.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.platform.GameIndexService;
import com.onelive.common.model.dto.common.CurrentUserCountryLangDTO;
import com.onelive.common.model.dto.index.IndexTopGameListDTO;
import com.onelive.common.mybatis.entity.GameIndex;
import com.onelive.common.mybatis.mapper.master.game.GameIndexMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameIndexMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private SlaveGameIndexMapper slaveGameIndexMapper;

    @Override
    public List<IndexTopGameListDTO> queryIndexTopGameList() {
        CurrentUserCountryLangDTO dto = new CurrentUserCountryLangDTO();
        return slaveGameIndexMapper.queryIndexTopGameList(dto);
    }
}
