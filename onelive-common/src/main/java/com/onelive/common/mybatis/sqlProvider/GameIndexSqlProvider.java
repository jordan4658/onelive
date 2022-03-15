package com.onelive.common.mybatis.sqlProvider;

import com.onelive.common.model.dto.common.CurrentUserCountryLangDTO;

/**
 * 首页游戏列表查询相关
 */
public class GameIndexSqlProvider {
    /**
     * 查询首页游戏配置列表
     * @return
     */
    public String queryIndexTopGameList(CurrentUserCountryLangDTO dto){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ix.id, IF( ISNULL( la.`name` )|| LENGTH( TRIM( la.`name` ))< 1, ix.`name`, la.`name` ) `name`,  ix.icon_url, ")
            .append("ix.skip_url, ix.skip_model, ix.skip_type, ix.category_id, ix.game_code, ix.studio_num, ix.source, ix.route, ix.params ")
            .append(" FROM game_index ix LEFT JOIN game_index_lang la ON la.game_index_id = ix.id ")
            .append("WHERE la.lang = '").append(dto.getLang()).append("'  AND ix.is_delete =0 AND ix.is_show = 1")
            .append(" AND ix.country_code='").append(dto.getCountryCode()).append("'");
        return sb.toString();
    }
}
