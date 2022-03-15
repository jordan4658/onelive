package com.onelive.common.mybatis.sqlProvider;

import cn.hutool.core.text.StrBuilder;
import com.onelive.common.model.dto.common.CurrentUserCountryLangDTO;

/**
 * 直播间游戏标签查询内容
 */
public class LiveGameTagProvider {

    /**
     * 查询当前国家和语言的游戏标签列表
     * @param dto
     * @return
     */
    public String listWithLang(CurrentUserCountryLangDTO dto){
        StrBuilder sb = new StrBuilder();
        sb.append("SELECT " +
                " tag.id, " +
                " tag.code, " +
                " lang.`name` " +
                "FROM " +
                " live_game_tag tag " +
                " LEFT JOIN live_game_tag_lang lang ON tag.id=lang.tag_id " +
                "WHERE " +
                " tag.is_delete = 0  " +
                " AND tag.is_show = 1  " +
                " AND FIND_IN_SET( '")
                .append(dto.getCountryCode())
                .append("', tag.country_code_list )  " +
                " AND  lang.lang = '")
                .append(dto.getLang())
                .append("'");
        return sb.toString();
    }
}
