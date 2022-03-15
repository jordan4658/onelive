package com.onelive.api.service.game.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.game.GameCenterGameService;
import com.onelive.common.model.dto.common.CurrentUserCountryLangDTO;
import com.onelive.common.model.req.game.GameListByTagReq;
import com.onelive.common.mybatis.entity.GameCenterGame;
import com.onelive.common.mybatis.mapper.master.game.GameCenterGameMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameCenterGameMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 彩票-国家对应表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-27
 */
@Service
public class GameCenterGameServiceImpl extends ServiceImpl<GameCenterGameMapper, GameCenterGame> implements GameCenterGameService {

    @Resource
    private SlaveGameCenterGameMapper slaveGameCenterGameMapper;

    @Override
    public List<GameCenterGame> listByTagWithLang(GameListByTagReq req) {
        return slaveGameCenterGameMapper.listByTagWithLang(req);
    }

    @Override
    public List<GameCenterGame> listWithCurrentLang() {
        CurrentUserCountryLangDTO dto = new CurrentUserCountryLangDTO();
        List<GameCenterGame> list = slaveGameCenterGameMapper.listWithCurrentLang(dto);
        return list;
    }
}
