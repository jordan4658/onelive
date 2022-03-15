package com.onelive.common.mybatis.mapper.slave.game;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.common.CurrentUserCountryLangDTO;
import com.onelive.common.mybatis.entity.GameTag;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 游戏分类标签, 用于前端显示 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-21
 */
public interface SlaveGameTagMapper extends BaseMapper<GameTag> {

    @Select("SELECT " +
            "  tag.id," +
            "  IF(ISNULL(lang.`name`) || LENGTH(trim(lang.`name`))<1,tag.`name`,lang.`name`) `name`, " +
            "  tag.`code`," +
            "  tag.country_code," +
            "  tag.sort,   " +
            "  tag.icon_url,   " +
            "  tag.is_show    " +
            "  FROM   " +
            "       game_tag tag   " +
            "  LEFT JOIN game_tag_lang lang ON lang.tag_id = tag.id   AND  lang.lang = #{lang}   " +
            "  WHERE   " +
            "    tag.is_delete = 0    " +
            "    AND tag.is_show = 1    " +
            "    AND tag.country_code = #{countryCode} ")
    List<GameTag> listWithCurrentLang(CurrentUserCountryLangDTO dto);

}
