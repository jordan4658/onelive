package com.onelive.common.mybatis.mapper.slave.live;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.common.CurrentUserCountryLangDTO;
import com.onelive.common.mybatis.entity.LiveGameTag;
import com.onelive.common.mybatis.sqlProvider.LiveGameTagProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-06
 */
public interface SlaveLiveGameTagMapper extends BaseMapper<LiveGameTag> {

    @SelectProvider(type = LiveGameTagProvider.class, method = "listWithLang")
    List<LiveGameTag> listWithLang(CurrentUserCountryLangDTO dto);
}
