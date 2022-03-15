package com.onelive.common.mybatis.mapper.slave.game;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.common.CurrentUserCountryLangDTO;
import com.onelive.common.model.req.game.GameListByTagReq;
import com.onelive.common.mybatis.entity.GameCenterGame;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 彩票-国家对应表 Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-01-21
 */
public interface SlaveGameCenterGameMapper extends BaseMapper<GameCenterGame> {
    
    @Select("SELECT " +
            "            game.id, " +
            "            IFNULL( lang.`name`, game.`name` ) `name`, " +
            "            game.category_id, " +
            "            game.game_code, " +
            "            game.code, " +
            "            game.icon_url " +
            "        FROM " +
            "            game_center_game game " +
            "                LEFT JOIN game_center_game_lang lang ON lang.game_id = game.id " +
            "        WHERE " +
            "            game.is_delete = 0 " +
            "          AND lang.lang = #{lang} " +
            "          AND game.code = #{code}")
    List<GameCenterGame> listByTagWithLang(GameListByTagReq req);


    @Select("SELECT " +
            " game.id, " +
            " game.`code`, " +
            " game.game_code, " +
            " game.category_id, " +
            " game.icon_url, " +
            " IF(ISNULL(lang.`name`) || LENGTH(trim(lang.`name`))<1,game.`name`,lang.`name`) `name` " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  g.*  " +
            " FROM " +
            "  game_center_game g, " +
            "  game_third third  " +
            " WHERE " +
            "  g.game_code = third.game_code  " +
            "  AND g.is_delete = 0  " +
            "  AND g.is_show = 1  " +
            "  AND g.status = 2  " +
            "  AND third.is_work = 1  " +
            "  AND third.STATUS = 2  " +
            " ) AS game " +
            " LEFT JOIN game_center_game_lang lang ON lang.game_id = game.id  AND  lang.lang = #{lang}")
    List<GameCenterGame> listWithCurrentLang(CurrentUserCountryLangDTO dto);
}
