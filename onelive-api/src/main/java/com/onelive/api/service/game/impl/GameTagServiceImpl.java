package com.onelive.api.service.game.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.game.GameTagService;
import com.onelive.common.constants.sys.LangConstants;
import com.onelive.common.model.dto.common.CurrentUserCountryLangDTO;
import com.onelive.common.mybatis.entity.GameTag;
import com.onelive.common.mybatis.mapper.master.game.GameTagMapper;
import com.onelive.common.mybatis.mapper.slave.game.SlaveGameTagMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 游戏分类标签, 用于前端显示 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-27
 */
@Service
public class GameTagServiceImpl extends ServiceImpl<GameTagMapper, GameTag> implements GameTagService {

    @Resource
    private SlaveGameTagMapper slaveGameTagMapper;

    @Override
    public List<GameTag> listWithCurrentLang() {
        CurrentUserCountryLangDTO dto = new CurrentUserCountryLangDTO();
        List<GameTag> list = slaveGameTagMapper.listWithCurrentLang(dto);
        return list;
    }
}
