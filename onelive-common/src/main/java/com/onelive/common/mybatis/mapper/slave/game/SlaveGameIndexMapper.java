package com.onelive.common.mybatis.mapper.slave.game;

import com.onelive.common.model.dto.common.CurrentUserCountryLangDTO;
import com.onelive.common.model.dto.index.IndexTopGameListDTO;
import com.onelive.common.mybatis.entity.GameIndex;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.mybatis.sqlProvider.GameIndexSqlProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 * 首页游戏内容配置 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-06
 */
public interface SlaveGameIndexMapper extends BaseMapper<GameIndex> {
    /**
     * 查询首页游戏配置列表
     * @return
     */
    @SelectProvider(type = GameIndexSqlProvider.class, method = "queryIndexTopGameList")
    public List<IndexTopGameListDTO> queryIndexTopGameList(CurrentUserCountryLangDTO dto);
}
